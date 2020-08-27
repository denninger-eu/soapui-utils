package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.test.SuuTestStepScript

class TestStepScriptJaxb(
    private val element: TestStepElement
) : TestStepJaxb(element), SuuTestStepScript {

    override var script: String?
        get() = element.config?.getValue("script")
        set(value) {
            TODO("implement")
        }

}