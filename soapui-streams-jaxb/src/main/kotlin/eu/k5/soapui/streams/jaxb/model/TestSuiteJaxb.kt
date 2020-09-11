package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.PropertiesElement
import eu.k5.soapui.streams.jaxb.element.TestSuiteElement
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite

class TestSuiteJaxb(
    private val element: TestSuiteElement
) : SuuTestSuite {

    override var name: String
        get() = element.name ?: ""
        set(value) {
            element.name = value
        }
    override var enabled: Boolean
        get() = element.disabled?.not() ?: true
        set(value) {
            element.disabled = !value
        }

    override val properties: SuuProperties
        get() = PropertiesJaxb(element.properties ?: PropertiesElement())
    override val testCases: List<SuuTestCase>
        get() = element.testCases?.map { TestCaseJaxb(it, this) } ?: emptyList()

    override fun createTestCase(name: String): SuuTestCase {
        TODO("Not yet implemented")
    }

}