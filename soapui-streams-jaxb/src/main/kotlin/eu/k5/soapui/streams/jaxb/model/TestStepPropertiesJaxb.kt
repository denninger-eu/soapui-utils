package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.ConfigPropertiesStepElement
import eu.k5.soapui.streams.jaxb.element.ConfigRestRequestStepElement
import eu.k5.soapui.streams.jaxb.element.PropertiesElement
import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStepProperties

class TestStepPropertiesJaxb(
    testStep: TestStepElement
) : TestStepJaxb(testStep), SuuTestStepProperties {

    private val config: ConfigPropertiesStepElement = element.config as ConfigPropertiesStepElement

    override val properties: SuuProperties
        get() = PropertiesJaxb(config.properties ?: PropertiesElement())

}