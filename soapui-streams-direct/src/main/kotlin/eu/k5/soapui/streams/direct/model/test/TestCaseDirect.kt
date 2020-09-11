package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestResource
import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.*
import com.eviware.soapui.impl.wsdl.teststeps.registry.RestRequestStepFactory
import com.eviware.soapui.model.testsuite.TestStep
import eu.k5.soapui.streams.direct.model.PropertiesDirect
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.*

class TestCaseDirect(
    private val testCase: WsdlTestCase,
    override val suite: TestSuiteDirect
) : SuuTestCase {
    override fun createWsdlRequestStep(name: String, operation: String): SuuTestStepWsdlRequest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val weights = HashMap<TestStep, Int>()
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
        }.mapIndexed { index, step -> mapTestStep(this, step) }.toMutableList()


    override val properties: SuuProperties
        get() = PropertiesDirect(testCase) { testCase.addProperty(it) }

    override fun <T : SuuTestStep> createStep(name: String, type: Class<T>): T {
        if (type == SuuTestStepPropertyTransfers::class.java) {
            return TestStepPropertyTransfersDirect(
                this,
                testCase.addTestStep(
                    "transfer",
                    name
                ) as PropertyTransfersTestStep
            ) as T
        } else if (type == SuuTestStepDelay::class.java) {
            return TestStepDelayDirect(this, testCase.addTestStep("delay", name) as WsdlDelayTestStep)
                    as T
        } else if (type == SuuTestStepRestRequest::class.java) {
            return TestStepRestRequestDirect(
                this,
                testCase.addTestStep("restrequest", name) as RestTestRequestStep
            ) as T
        } else if (type == SuuTestStepProperties::class.java) {
            return TestStepPropertiesDirect(
                this,
                testCase.addTestStep("properties", name) as WsdlPropertiesTestStep
            ) as T
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createScriptStep(name: String): SuuTestStepScript {
        val newTestStep = testCase.addTestStep("groovy", name)

        return TestStepScriptDirect(this, newTestStep as WsdlGroovyScriptTestStep)
    }

    private fun findRestServiceOrCreate(restService: SuuRestService): RestService {
        val existingServices = testCase.project.interfaceList.filterIsInstance(RestService::class.java)
        val existing = existingServices.firstOrNull { it.name == restService.name }
        if (existing != null) {
            return existing
        }
        TODO("create restservice")
    }

    override fun createRestRequestStep(
        name: String,
        restService: SuuRestService,
        restResources: List<SuuRestResource>,
        restMethod: SuuRestMethod
    ): SuuTestStepRestRequest {

        val existingRestService = findRestServiceOrCreate(restService)
        val existingRestResource = findRestResourceOrCreate(existingRestService, restResources)
        val existingMethod = findRestMethodOrCreate(existingRestResource, restMethod)


        val config = RestRequestStepFactory().createNewTestStep(existingMethod, name)
        val testStep = testCase.addTestStep(config)

        return TestStepRestRequestDirect(this, testStep as RestTestRequestStep)

    }


    private fun findRestMethodOrCreate(restResource: RestResource, restMethod: SuuRestMethod): RestMethod {
        val existingRestMethod = restResource.restMethodList.firstOrNull { it.name == restMethod.name }
        if (existingRestMethod != null) {
            return existingRestMethod
        }
        TODO("create")
    }

    private fun findRestResourceOrCreate(restService: RestService, restResources: List<SuuRestResource>): RestResource {
        var existingResource = restService.resourceList.firstOrNull { it.name == restResources[0].name }
        if (restResources.size > 1) {
            for (restResource in restResources.subList(1, restResources.size)) {
                existingResource = existingResource?.childResourceList?.firstOrNull { it.name == restResource.name }
                if (existingResource == null) {
                    TODO("create")
                }
            }
        }
        if (existingResource != null) {
            return existingResource
        }
        TODO("implement create")
    }


    companion object {
        private val stepFactories = HashMap<Class<out Any>, (TestCaseDirect, TestStep) -> AbstractTestStepDirect>()

        init {
            stepFactories[WsdlDelayTestStep::class.java] = { testCase, step ->
                TestStepDelayDirect(testCase, step as WsdlDelayTestStep)
            }
            stepFactories[PropertyTransfersTestStep::class.java] = { testCase, step ->
                TestStepPropertyTransfersDirect(testCase, step as PropertyTransfersTestStep)
            }
            stepFactories[RestTestRequestStep::class.java] = { testCase, step ->
                TestStepRestRequestDirect(testCase, step as RestTestRequestStep)
            }
            stepFactories[WsdlPropertiesTestStep::class.java] = { testCase, step ->
                TestStepPropertiesDirect(testCase, step as WsdlPropertiesTestStep)
            }
            stepFactories[WsdlGroovyScriptTestStep::class.java] = { testCase, step ->
                TestStepScriptDirect(testCase, step as WsdlGroovyScriptTestStep)
            }
            stepFactories[WsdlTestRequestStep::class.java] = { testCase, step ->
                TestStepWsdlRequestDirect(testCase, step as WsdlTestRequestStep)
            }
        }

        fun supported(testStep: TestStep): Boolean = stepFactories.containsKey(testStep.javaClass)


        fun mapTestStep(
            testCase: TestCaseDirect,
            testStep: TestStep
        ): AbstractTestStepDirect {
            val factory = stepFactories[testStep.javaClass]!!
            return factory(testCase, testStep)
        }
    }

    private fun getIndex(testStep: TestStep): Int {
        return testCase.testStepList.indexOf(testStep)
    }

    override fun isLostAndFound(): Boolean {
        val property = testCase.properties["lostAndFound"]
            ?: return false
        return property.value == "true"
    }

    override fun markLostAndFound() {
        name = "LF_$name"
        enabled = false
        properties.addOrUpdate("lostAndFound", "true")
    }

    override fun reorderSteps(): Boolean {
        var modified = false
        val sortedSteps = steps.sortedBy { it.weight }
        for ((target, step) in sortedSteps.withIndex()) {

            val current = testCase.testStepList.indexOf(step.testStep)

            if (target != current) {
                testCase.moveTestStep(current, target - current)
                modified = true
            }
        }
        return modified
    }

    fun getWeight(testStep: WsdlTestStep): Int {
        return if (weights.containsKey(testStep)) {
            weights[testStep] ?: 0
        } else {
            testCase.testStepList.indexOf(testStep) * 10
        }
    }

    fun setWeight(testStep: WsdlTestStep, value: Int) {
        weights[testStep] = value
    }

}