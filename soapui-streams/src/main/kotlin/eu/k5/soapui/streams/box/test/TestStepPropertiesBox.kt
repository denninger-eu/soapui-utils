package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.PropertiesBox
import eu.k5.soapui.streams.box.YamlContext
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStepProperties

class TestStepPropertiesBox(
    private val box: Box,
    private val yaml: PropertiesYaml = box.load(
        PropertiesYaml::class.java
    ) ?: PropertiesYaml()

) : TestStepBox(yaml), SuuTestStepProperties {

    override fun path(): String {
        return box.path.fileName.toString()
    }
    override fun store() {
        box.write(yaml)
    }
    override val properties: SuuProperties
            by lazy { PropertiesBox(yaml.properties!!) { store() } }

    class PropertiesYaml : TestStepYaml() {
        var properties: MutableList<PropertiesBox.PropertyYaml>? = ArrayList()

    }

    companion object {
        fun create(parent: Box, name: String): TestStepPropertiesBox {
            return TestStepPropertiesBox(createBox(parent, name, TestStepPropertiesBox.PropertiesYaml()))

        }
    }
}