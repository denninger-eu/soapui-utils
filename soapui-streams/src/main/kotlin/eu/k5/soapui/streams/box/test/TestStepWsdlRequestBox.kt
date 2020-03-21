package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.box.rest.RestMethodBox
import eu.k5.soapui.streams.box.rest.RestRequestBox
import eu.k5.soapui.streams.box.rest.RestResourceBox
import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.box.wsdl.WsdlRequestBox
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.streams.model.test.SuuTestStepWsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class TestStepWsdlRequestBox(
    private val box: Box,
    private val yaml: WsdlRequestYaml = box.load(
        WsdlRequestYaml::class.java
    )
) : TestStepBox(yaml), SuuTestStepWsdlRequest {


    private val assertionsYaml: AssertionsBox.AssertionsYaml =
        box.load(AssertionsBox.AssertionsYaml::class.java, "assertions")
            ?: AssertionsBox.AssertionsYaml()

    override val assertions: AssertionsBox = AssertionsBox(assertionsYaml) { storeAssertions() }


    override var operationName: String
        get() = yaml.operationName ?: ""
        set(value) {
            if (changed(yaml.operationName, value)) {
                yaml.operationName = value
                store()
            }
        }

    override fun path(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun store() {
        box.write(yaml)
    }

    override val operation: SuuWsdlOperation
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val request: SuuWsdlRequest
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    private fun storeAssertions() {
        box.write(assertionsYaml, "assertions")
    }


    class WsdlRequestLocalBox(
        private val box: Box,
        private val yaml: WsdlRequestBox.WsdlRequestYaml,
        private val store: () -> Unit
    ) : SuuWsdlRequest {

        override var name: String
            get() = yaml.name ?: ""
            set(value) {
                if (changed(yaml.name, value)) {
                    yaml.name = value
                    store()
                }
            }

        override var description: String?
            get() = yaml.description
            set(value) {
                if (changed(yaml.description, value)) {
                    yaml.description = value
                    store()
                }
            }
        override var content: String
            get() = box.loadSection("content") ?: ""
            set(value) {
                if (changed(yaml.description, value)) {
                    yaml.description = value
                    store()
                }
            }

        override fun markLostAndFound() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override val headers: List<Header>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

        override fun removeHeader(key: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun addOrUpdateHeader(header: Header) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

    class WsdlRequestYaml : TestStepBox.TestStepYaml() {
        var operationName: String? = null
        var request: WsdlRequestBox.WsdlRequestYaml? = null
    }

    companion object {
        fun create(parent: Box, name: String): TestStepWsdlRequestBox {
            return TestStepWsdlRequestBox(createBox(parent, name, WsdlRequestYaml()))
        }
    }
}