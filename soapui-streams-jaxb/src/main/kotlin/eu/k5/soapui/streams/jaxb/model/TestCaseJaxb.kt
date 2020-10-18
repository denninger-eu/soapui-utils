package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.PropertiesElement
import eu.k5.soapui.streams.jaxb.element.TestCaseElement
import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.*

class TestCaseJaxb(
    private val element: TestCaseElement,
    override val suite: TestSuiteJaxb
) : SuuTestCase {

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

    override val steps: List<SuuTestStep>
        get() = element.testSteps?.filter {
            supported(
                it
            )
        }?.mapIndexed { index, step -> mapTestStep(this, step, suite.project) } ?: ArrayList()

    override fun <T : SuuTestStep> createStep(name: String, java: Class<T>): T {
        TODO("Not yet implemented")
    }

    override fun markLostAndFound() {
        TODO("Not yet implemented")
    }

    override fun isLostAndFound(): Boolean {
        TODO("Not yet implemented")
    }

    override fun createRestRequestStep(
        name: String,
        restService: SuuRestService,
        restResources: List<SuuRestResource>,
        restMethod: SuuRestMethod
    ): SuuTestStepRestRequest {
        TODO("Not yet implemented")
    }

    override fun createScriptStep(name: String): SuuTestStepScript {
        TODO("Not yet implemented")
    }

    override fun createWsdlRequestStep(name: String, operation: String): SuuTestStepWsdlRequest {
        TODO("Not yet implemented")
    }

    override fun reorderSteps(): Boolean {
        TODO("Not yet implemented")
    }

    companion object {

        fun supported(testStep: TestStepElement): Boolean {
            return true
        }

        fun mapTestStep(testCaseJaxb: TestCaseJaxb, testStep: TestStepElement, project: ProjectJaxb): TestStepJaxb {
            if (testStep.type == "delay") {
                return TestStepDelayJaxb(testStep)
            } else if (testStep.type == "groovy") {
                return TestStepScriptJaxb(testStep)
            } else if (testStep.type == "transfer") {
                return TestStepPropertyTransfersJaxb(testStep)
            } else if (testStep.type == "properties") {
                return TestStepPropertiesJaxb(testStep)
            } else if (testStep.type == "restrequest") {
                return TestStepRestRequestJaxb(testStep, project)
            } else if (testStep.type == "jdbc") {
                return TestStepDelayJaxb(testStep)

            } else if (testStep.type == "goto") {
                return TestStepGotoJaxb(testStep)
            }
            TODO("unsupported " + testStep.type)
        }
    }

}