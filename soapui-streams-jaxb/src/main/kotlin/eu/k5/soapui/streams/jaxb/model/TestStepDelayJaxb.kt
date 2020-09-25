package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.test.SuuTestStepDelay

class TestStepDelayJaxb(
    private val element: TestStepElement
) : TestStepJaxb(element), SuuTestStepDelay {

    override var delay: Int
        get() = element.config?.getValue("delay")?.toInt() ?: 0
        set(value) {
            TODO("test")
        }

}