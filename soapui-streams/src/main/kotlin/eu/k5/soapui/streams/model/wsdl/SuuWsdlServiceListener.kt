package eu.k5.soapui.streams.model.wsdl

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject

interface SuuWsdlServiceListener {


    fun enter(suuWsdlService: SuuWsdlService): VisitResult
    fun exit(suuWsdlService: SuuWsdlService)


    fun enterOperation(suuWsdlOperation: SuuWsdlOperation): VisitResult
    fun exitOperation(suuWsdlOperation: SuuWsdlOperation)
    fun handleRequest(suuWsdlRequest: SuuWsdlRequest)
    fun enterProject(suProject: SuProject)
    fun exitProject(suProject: SuProject)

    companion object {
        val NO_OP = object : SuuWsdlServiceListener {
            override fun enter(suuWsdlService: SuuWsdlService): VisitResult {
                return VisitResult.TERMINATE
            }

            override fun exit(suuWsdlService: SuuWsdlService) {
            }

            override fun enterOperation(suuWsdlOperation: SuuWsdlOperation): VisitResult {
                return VisitResult.TERMINATE
            }

            override fun exitOperation(suuWsdlOperation: SuuWsdlOperation) {
            }

            override fun handleRequest(suuWsdlRequest: SuuWsdlRequest) {
            }

            override fun enterProject(suProject: SuProject) {
            }

            override fun exitProject(suProject: SuProject) {
            }

        }
    }
}
