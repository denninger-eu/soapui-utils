package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep
import com.eviware.soapui.model.testsuite.TestStep
import eu.k5.soapui.streams.direct.model.PropertiesDirect
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.*

class TestCaseDirect(
    private val testCase: WsdlTestCase
) : SuuTestCase {


    override var name: String
        get() = testCase.name
        set(value) {
            testCase.name = value
        }
    override var enabled: Boolean
        get() = !testCase.isDisabled
        set(value) {
            testCase.isDisabled = !value
        }

    override val steps
        get() = testCase.testStepList.filter {
            supported(
                it
            )
        }.map { mapTestStep(it) }.toMutableList()


    override val properties: SuuProperties
        get() = PropertiesDirect(testCase) { testCase.addProperty(it) }

    override fun <T : SuuTestStep> createStep(name: String, type: Class<T>): T {
        if (type == SuuTestStepPropertyTransfers::class.java) {
            return TestStepPropertyTransfersDirect(
                testCase.addTestStep(
                    "transfer",
                    name
                ) as PropertyTransfersTestStep
            ) as T
        } else if (type == SuuTestStepDelay::class.java) {
            return TestStepDelayDirect(testCase.addTestStep("delay", name) as WsdlDelayTestStep) as T
        } else if (type == SuuTestStepRestRequest::class.java) {
            return TestStepRestRequestDirect(testCase.addTestStep("restrequest", name) as RestTestRequestStep) as T
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val stepFactories = HashMap<Class<out Any>, (TestStep) -> SuuTestStep>()

        init {
            stepFactories[WsdlDelayTestStep::class.java] = {
                TestStepDelayDirect(it as WsdlDelayTestStep)
            }
            stepFactories[PropertyTransfersTestStep::class.java] =
                { TestStepPropertyTransfersDirect(it as PropertyTransfersTestStep) }

            stepFactories[RestTestRequestStep::class.java] = {
                TestStepRestRequestDirect(it as RestTestRequestStep)
            }
        }

        fun supported(testStep: TestStep): Boolean = stepFactories.containsKey(testStep.javaClass)


        fun mapTestStep(testStep: TestStep): SuuTestStep {
            val factory = stepFactories[testStep.javaClass]!!
            return factory(testStep)
        }
    }

}