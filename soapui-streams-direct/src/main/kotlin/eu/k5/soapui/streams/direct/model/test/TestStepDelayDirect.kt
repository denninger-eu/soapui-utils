package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep
import eu.k5.soapui.streams.model.test.SuuTestStepDelay
import java.util.*

class TestStepDelayDirect(
    private val delayStep: WsdlDelayTestStep
) : AbstractTestStepDirect(delayStep, Arrays.asList("delay")), SuuTestStepDelay {

    override var delay: Int
        get() = Integer.parseInt(delayStep.delayString)
        set(value) {
            delayStep.delayString = value.toString()
        }

}