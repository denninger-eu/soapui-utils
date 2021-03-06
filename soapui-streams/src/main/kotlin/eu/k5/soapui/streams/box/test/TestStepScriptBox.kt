package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.model.test.SuuTestStepScript

class TestStepScriptBox(
    private val box: Box,
    private val yaml: ScriptYaml = box.load(ScriptYaml::class.java)
) : TestStepBox(yaml), SuuTestStepScript {

    override fun path(): String {
        return box.path.fileName.toString()
    }
    override var script: String?
        get() = box.loadSection("script")
        set(value) {
            if (script != value) {
                box.writeSection("script", value)
            }
        }


    override fun store() {
        box.write(yaml)
    }

    class ScriptYaml : TestStepYaml() {
    }

    companion object {
        fun create(parent: Box, name: String): TestStepScriptBox {
            return TestStepScriptBox(createBox(parent, name, ScriptYaml()))
        }
    }
}