package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.test.SuuTestStep

abstract class TestStepJaxb(
     val element: TestStepElement
) : SuuTestStep {
    override var name: String
        get() = element.name ?: ""
        set(value) {
            element.name = value
        }
    override var description: String?
        get() = element.description
        set(value) {
            element.description = value
        }
    override var enabled: Boolean
        get() = element.disabled?.not() ?: true
        set(value) {
            element.disabled = !value
        }
    override var weight: Int
        get() = TODO("Not yet implemented")
        set(value) {}

}