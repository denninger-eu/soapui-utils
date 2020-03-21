package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

interface SuuTestStepWsdlRequest : SuuTestStep {
    val operationName: String
    val operation: SuuWsdlOperation
    val request: SuuWsdlRequest
    val assertions: SuuAssertions
}