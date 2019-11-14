package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest

class TestStepRestRequestBox(
    private val box: Box,
    private val yaml: TestStepRestRequestBox.RestRequestYaml = box.load(
        TestStepBox.YAML_LOAD,
        RestRequestYaml::class.java
    )

) : TestStepBox(yaml), SuuTestStepRestRequest {

    private val assertionsYaml: AssertionsBox.AssertionsYaml =
        box.load(YAML_LOAD, AssertionsBox.AssertionsYaml::class.java, "assertions")


    override val assertions: AssertionsBox = AssertionsBox(assertionsYaml) { storeAssertions() }

    override var request: SuuRestRequest
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}


    override fun store() {
        box.write(YAML_DUMPER, yaml)
    }

    fun storeAssertions() {
        box.write(YAML_DUMPER, assertionsYaml, "assertions")
    }

    class RestRequestYaml : TestStepYaml() {

    }

    companion object {
        fun create(parent: Box, name: String): TestStepRestRequestBox {
            return TestStepRestRequestBox(createBox(parent, name, TestStepRestRequestBox.RestRequestYaml()))

        }

    }
}