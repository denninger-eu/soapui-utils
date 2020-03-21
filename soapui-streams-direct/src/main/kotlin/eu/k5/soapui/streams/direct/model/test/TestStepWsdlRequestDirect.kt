package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import eu.k5.soapui.streams.direct.model.assertions.AssertionsDirect
import eu.k5.soapui.streams.direct.model.wsdl.WsdlOperationDirect
import eu.k5.soapui.streams.direct.model.wsdl.WsdlRequestDirect
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.test.SuuTestStepWsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class TestStepWsdlRequestDirect(
    testCase: TestCaseDirect,
    private val wsdlTestStep: WsdlTestRequestStep
) : AbstractTestStepDirect(
    testCase,
    wsdlTestStep
), SuuTestStepWsdlRequest {

    override val operationName: String
        get() = wsdlTestStep.operationName

    override val assertions: SuuAssertions
        get() = AssertionsDirect(wsdlTestStep.assertionList) { wsdlTestStep.addAssertion(it) }

    override val operation: SuuWsdlOperation = WsdlOperationDirect(wsdlTestStep.operation)

    override val request: SuuWsdlRequest = WsdlRequestDirect(wsdlTestStep.httpRequest)

}