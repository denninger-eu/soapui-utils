package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import eu.k5.soapui.streams.model.test.SuuTestStepWsdlRequest

class TestStepWsdlRequestDirect(
    testCase: TestCaseDirect,
    private val wsdlTestStep: WsdlTestRequestStep
) : AbstractTestStepDirect(
    testCase,
    wsdlTestStep
), SuuTestStepWsdlRequest {

}