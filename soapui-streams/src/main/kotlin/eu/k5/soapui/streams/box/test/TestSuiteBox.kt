package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.box.PropertiesBox
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite

class TestSuiteBox(
    private val box: Box
) : SuuTestSuite {

    private val yaml: TestSuiteYaml = box.load(TestSuiteYaml::class.java)

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


    override val testCases: List<TestCaseBox>
        get() = allTestCase.filter { !it.isLostAndFound() }.toList()

    private val allTestCase: MutableList<TestCaseBox> by lazy {
        box.findSubFolderBox { it.fileName.toString() == TestCaseBox.FILE_NAME }.map { TestCaseBox(it) }
            .toMutableList()
    }

    override fun createTestCase(name: String): SuuTestCase {
        val init = allTestCase
        val newTestCase = TestCaseBox.create(box, name)
        init.add(newTestCase)
        return newTestCase
    }

    private fun store() {
        box.write(TestSuiteYaml::class.java, yaml)
    }

    class TestSuiteYaml {
        var name: String? = null
        var enabled: Boolean? = null
        var properties: MutableList<PropertiesBox.PropertyYaml>? = ArrayList()
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