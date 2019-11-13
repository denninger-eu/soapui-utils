package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep
import eu.k5.soapui.streams.model.test.SuuTestStepDelay

class TestStepDelayDirect(
    private val delayStep: WsdlDelayTestStep
) : AbstractTestStepDirect(delayStep), SuuTestStepDelay {

    override var delay: Int
        get() = delayStep.delay
        set(value) {
            delayStep.delay = value
        }

}