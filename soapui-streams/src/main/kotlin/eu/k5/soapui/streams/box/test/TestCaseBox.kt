package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.PropertiesBox
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStep

class TestCaseBox(
    private val box: Box
) : SuuTestCase {
    private val testCase = box.load(TestCaseYaml::class.java)

    override var name: String
        get() = testCase.name ?: ""
        set(value) {
            if (testCase.name != value) {
                testCase.name = value
                store()
            }
        }
    override var enabled: Boolean
        get() = testCase.enabled ?: true
        set(value) {
            if (testCase.enabled != value) {
                testCase.enabled = value
                store()
            }
        }

    override val properties: SuuProperties
            by lazy { PropertiesBox(testCase.properties!!) { store() } }

    override val steps: List<SuuTestStep>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    class TestCaseYaml {
        var name: String? = null
        var enabled: Boolean? = null
        var properties: MutableList<PropertiesBox.PropertyYaml>? = ArrayList()

    }

    fun store() {
        box.write(TestCaseBox.TestCaseYaml::class.java, testCase)
    }

    companion object {
        const val FILE_NAME = "testcase.box.yaml"

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