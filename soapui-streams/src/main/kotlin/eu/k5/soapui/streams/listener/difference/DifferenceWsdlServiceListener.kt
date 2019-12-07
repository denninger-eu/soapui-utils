package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.listener.sync.SyncMisc
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener

class DifferenceWsdlServiceListener(
    private val differences: Differences,
    private val referenceProject: SuProject
) : SuuWsdlServiceListener {

    private var referenceOperation: SuuWsdlOperation? = null
    private var referenceWsdlService: SuuWsdlService? = null

    override fun enter(wsdlService: SuuWsdlService): VisitResult {
        val ref = referenceProject.getWsdlService(wsdlService.name)
        if (ref == null) {
            differences.addAdditional(Differences.EntityType.WSDL_SERVICE, wsdlService.name)
            return VisitResult.TERMINATE
        }
        differences.push(Differences.EntityType.WSDL_SERVICE, wsdlService.name)

        differences.addChange("name", ref.name, wsdlService.name)
        differences.addChange("description", ref.description, wsdlService.description)

        referenceWsdlService = ref
        return VisitResult.CONTINUE
    }

    override fun exit(suuWsdlService: SuuWsdlService) {
        differences.pop()
    }

    override fun enterOperation(suuWsdlOperation: SuuWsdlOperation): VisitResult {
        val ref = referenceWsdlService!!.getOperation(suuWsdlOperation.name)
        if (ref == null) {
            differences.addAdditional(Differences.EntityType.WSDL_OPERATION, suuWsdlOperation.name)
            return VisitResult.TERMINATE
        }
        differences.push(Differences.EntityType.WSDL_OPERATION, suuWsdlOperation.name)
        differences.addChange("description", ref.description, suuWsdlOperation.description)
        referenceOperation = ref
        return VisitResult.CONTINUE

    }

    override fun exitOperation(suuWsdlOperation: SuuWsdlOperation) {
        referenceOperation = null
        differences.pop()
    }

    override fun handleRequest(suuWsdlRequest: SuuWsdlRequest) {
        val ref = referenceOperation!!.getRequest(suuWsdlRequest.name)
        if (ref == null) {
            differences.addAdditional(Differences.EntityType.WSDL_REQUEST, suuWsdlRequest.name)
            return
        }
        differences.addChange("description", ref.description, suuWsdlRequest.description)
        differences.addChange("content", ref.content.trim(), suuWsdlRequest.content.trim())
    }

    override fun enterProject(suProject: SuProject) {
    }

    override fun exitProject(suProject: SuProject) {
        val names = suProject.wsdlServices.map { it.name }
        referenceProject.wsdlServices.filter { !names.contains(it.name) }
            .forEach { differences.addMissing(Differences.EntityType.WSDL_SERVICE, it.name) }
    }

}
