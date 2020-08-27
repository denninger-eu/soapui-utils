package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep
import eu.k5.soapui.streams.model.test.SuuTestStepDelay

class TestStepDelayDirect(
    testCase: TestCaseDirect,
    private val delayStep: WsdlDelayTestStep
) : AbstractTestStepDirect(testCase, delayStep), SuuTestStepDelay {

    override var delay: Int
        get() = Integer.parseInt(delayStep.delayString)
        set(value) {
            delayStep.delayString = value.toString()
        }

}