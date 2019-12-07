package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener

class CopyWsdlServiceListener(
    val suuProject: SuProject
) : SuuWsdlServiceListener {

    private var targetWsdlService: SuuWsdlService? = null

    private var targetOperation: SuuWsdlOperation? = null

    override fun enter(wsdlService: SuuWsdlService): VisitResult {
        targetWsdlService = suuProject.createWsdlService(wsdlService.name)
        targetWsdlService!!.description = wsdlService.description
        return VisitResult.CONTINUE
    }

    override fun exit(suuWsdlService: SuuWsdlService) {
        targetWsdlService = null
    }

    override fun enterOperation(suuWsdlOperation: SuuWsdlOperation): VisitResult {
        var operation = targetWsdlService!!.getOperation(suuWsdlOperation.name)
        if (operation == null) {
            operation = targetWsdlService!!.createOperation(suuWsdlOperation.name)
        }
        operation.description = suuWsdlOperation.description

        targetOperation = operation
        return VisitResult.CONTINUE
    }

    override fun exitOperation(suuWsdlOperation: SuuWsdlOperation) {
        targetOperation = null
    }

    override fun handleRequest(suuWsdlRequest: SuuWsdlRequest) {
        val newRequest = targetOperation!!.createRequest(suuWsdlRequest.name)
        newRequest.description = suuWsdlRequest.description
        newRequest.content = suuWsdlRequest.content

    }

    override fun enterProject(suProject: SuProject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitProject(suProject: SuProject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

