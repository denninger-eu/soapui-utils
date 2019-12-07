package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener

class SyncWsdlServiceListener(
    private val env: Environment,
    private val referenceProject: SuProject,
    private val targetProject: SuProject?
) : SuuWsdlServiceListener {

    private var referenceWsdlService: SuuWsdlService? = null
    private var referenceWsdlOperation: SuuWsdlOperation? = null

    override fun enter(suuWsdlService: SuuWsdlService): VisitResult {
        val ref = referenceProject.getWsdlService(suuWsdlService.name)
        if (ref == null) {
            suuWsdlService.markLostAndFound()
            return VisitResult.TERMINATE
        }
        referenceWsdlService = ref
        return VisitResult.CONTINUE
    }

    override fun exit(suuWsdlService: SuuWsdlService) {
    }

    override fun enterOperation(suuWsdlOperation: SuuWsdlOperation): VisitResult {
        val ref = referenceWsdlService!!.getOperation(suuWsdlOperation.name)
        if (ref == null) {
            suuWsdlOperation.markLostAndFound()
            return VisitResult.TERMINATE
        }
        suuWsdlOperation.description = ref.description
        referenceWsdlOperation = ref
        return VisitResult.CONTINUE
    }

    override fun exitOperation(suuWsdlOperation: SuuWsdlOperation) {

        referenceWsdlOperation = null
    }

    override fun handleRequest(suuWsdlRequest: SuuWsdlRequest) {
        val ref = referenceWsdlOperation!!.getRequest(suuWsdlRequest.name)
        if (ref == null) {
            suuWsdlRequest.markLostAndFound()
            return
        }
        suuWsdlRequest.description = ref.description
        suuWsdlRequest.content = ref.content
    }

    override fun enterProject(suProject: SuProject) {
    }

    override fun exitProject(suProject: SuProject) {
        val existing = suProject.wsdlServices.map { it.name }.toList()
        val copyListener = CopyWsdlServiceListener(targetProject!!)

        referenceProject.wsdlServices.filter { !existing.contains(it.name) }.forEach { handleMissing(copyListener, it) }
    }

    private fun handleMissing(copyListener: SuuWsdlServiceListener, missingWsdlService: SuuWsdlService) {
        missingWsdlService.apply(copyListener)
    }

}