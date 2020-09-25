package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStepProperties

class TestStepPropertiesJaxb(
    testStep: TestStepElement

) : TestStepJaxb(testStep), SuuTestStepProperties {
    override val properties: SuuProperties
        get() = TODO("Not yet implemented")

}