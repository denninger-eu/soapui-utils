package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.box.PropertiesBox
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.*

class TestCaseBox(
    private val box: Box
) : SuuTestCase {
    private val yaml = box.load(TestCaseYaml::class.java)


    override fun isLostAndFound(): Boolean = yaml.lostAndFound ?: false


    override fun markLostAndFound() {
        yaml.lostAndFound = true
        yaml.enabled = false
        store()
    }

    override var name: String
        get() = yaml.name ?: ""
        set(value) {
            if (changed(yaml.name, value)) {
                yaml.name = value
                store()
            }
        }
    override var enabled: Boolean
        get() = yaml.enabled ?: true
        set(value) {
            if (changed(yaml.enabled, value)) {
                yaml.enabled = value
                store()
            }
        }

    override val properties: SuuProperties
            by lazy { PropertiesBox(yaml.properties!!) { store() } }

    override val steps: List<SuuTestStep>
            by lazy { box.findOrderFiles().map { mapBox(it) } }


    override fun <T : SuuTestStep> createStep(name: String, type: Class<T>): T {
        return type.cast(createStep(box, name, type))
    }

    override fun createScriptStep(name: String): SuuTestStepScript {
        return TestStepScriptBox.create(box, name)
    }


    override fun createRestRequestStep(
        name: String,
        restService: SuuRestService,
        restResources: List<SuuRestResource>,
        restMethod: SuuRestMethod
    ): SuuTestStepRestRequest {
        val targetStep = createStep(name, SuuTestStepRestRequest::class.java) as TestStepRestRequestBox

        targetStep.setBaseRestService(restService)
        targetStep.setBaseResources(restResources)
        targetStep.setBaseMethod(restMethod)

        targetStep.name = name
        return targetStep
    }

    class TestCaseYaml {
        var name: String? = null
        var enabled: Boolean? = null
        var lostAndFound: Boolean? = null
        var properties: MutableList<PropertiesBox.PropertyYaml>? = ArrayList()
    }

    fun store() {
        box.write(TestCaseBox.TestCaseYaml::class.java, yaml)
    }

    companion object {
        const val FILE_NAME = "testcase.box.yaml"

        private val stepFactory = HashMap<Class<out Any>, (Box, String) -> SuuTestStep>()
        private val stepMapFactories = HashMap<Class<out Any>, (Box) -> SuuTestStep>()

        init {
            stepFactory[SuuTestStepPropertyTransfers::class.java] = { parent: Box, name: String ->
                TestStepPropertyTransfersBox.create(parent, name)
            }
            stepFactory[SuuTestStepDelay::class.java] = { parent: Box, name: String ->
                TestStepDelayBox.create(parent, name)
            }
            stepFactory[SuuTestStepRestRequest::class.java] = { parent: Box, name: String ->
                TestStepRestRequestBox.create(parent, name)
            }

            stepFactory[SuuTestStepProperties::class.java] = { parent: Box, name: String ->
                TestStepPropertiesBox.create(parent, name)
            }
        }

        fun <T : SuuTestStep> supported(type: Class<T>): Boolean = stepFactory.containsKey(type)


        fun mapBox(box: Box): TestStepBox {
            val load = box.load()
            if (load is TestStepPropertyTransfersBox.PropertyTransfersYaml) {
                return TestStepPropertyTransfersBox(box)
            } else if (load is TestStepDelayBox.DelayYaml) {
                return TestStepDelayBox(box)
            } else if (load is TestStepRestRequestBox.RestRequestYaml) {
                return TestStepRestRequestBox(box)
            } else if (load is TestStepPropertiesBox.PropertiesYaml) {
                return TestStepPropertiesBox(box)
            } else if (load is TestStepScriptBox.ScriptYaml) {
                return TestStepScriptBox(box)
            }
            TODO(load.javaClass.toString())
        }

        fun <T : SuuTestStep> createStep(parent: Box, name: String, type: Class<T>): T {
            return type.cast(stepFactory[type]!!(parent, name))
        }

        fun create(parent: Box, name: String): TestCaseBox {

            val box = parent.createFolder(name, FILE_NAME)
            val testCase = TestCaseBox.TestCaseYaml()
            testCase.name = name
            testCase.enabled = true
            box.write(TestCaseBox.TestCaseYaml::class.java, testCase)
            return TestCaseBox(box)
        }
    }
}