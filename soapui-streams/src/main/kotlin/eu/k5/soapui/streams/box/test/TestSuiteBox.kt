package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite

class TestSuiteBox(
    private val box: Box
) : SuuTestSuite {
    private val testSuite: TestSuiteYaml = box.load(TestSuiteYaml::class.java)

    override var name: String
        get() = testSuite.name ?: ""
        set(value) {
            if (testSuite.name != value) {
                testSuite.name = value
                store()
            }
        }
    override var enabled: Boolean
        get() = testSuite.enabled ?: true
        set(value) {
            if (testSuite.enabled != value) {
                testSuite.enabled
                store()
            }
        }


    override val testCases: List<SuuTestCase>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    private fun store() {
        box.write(TestSuiteYaml::class.java, testSuite)
    }

    class TestSuiteYaml {
        var name: String? = null
        var enabled: Boolean? = null

    }


    companion object {

        fun create(parent: Box, name: String): TestSuiteBox {

            val box = parent.createFolder(name, TestSuiteBox.FILE_NAME)
            val testSuite = TestSuiteBox.TestSuiteYaml()
            testSuite.name = name
            testSuite.enabled = true
            box.write(TestSuiteBox.TestSuiteYaml::class.java, testSuite)
            return TestSuiteBox(box)
        }

        const val FILE_NAME = "testsuite.box.yaml"

    }
}