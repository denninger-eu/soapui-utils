package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.model.test.SuuTestStepDelay

class TestStepDelayBox(
    private val box: Box,
    private val yaml: DelayYaml = box.load(DelayYaml::class.java)
) : TestStepBox(yaml), SuuTestStepDelay {
    override fun path(): String {
        return box.path.fileName.toString()
    }

    override var delay: Int
        get() = yaml.delay ?: 1000
        set(value) {
            if (yaml.delay != value) {
                yaml.delay = value
                store()
            }
        }

    override fun store() {
        box.write(yaml)
    }

    class DelayYaml : TestStepBox.TestStepYaml() {
        var delay: Int? = null
    }

    companion object {
        fun create(parent: Box, name: String): TestStepDelayBox {
            return TestStepDelayBox(createBox(parent, name, DelayYaml()))
        }
    }
}