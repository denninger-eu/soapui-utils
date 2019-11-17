package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.YamlContext
import eu.k5.soapui.streams.box.rest.*
import eu.k5.soapui.streams.model.rest.*
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import java.lang.UnsupportedOperationException
import java.util.*
import kotlin.collections.ArrayList

class TestStepRestRequestBox(
    private val box: Box,
    private val yaml: TestStepRestRequestBox.RestRequestYaml = box.load(
        YamlContext.YAML_LOAD,
        RestRequestYaml::class.java
    ) ?: RestRequestYaml()

) : TestStepBox(yaml), SuuTestStepRestRequest {

    private val assertionsYaml: AssertionsBox.AssertionsYaml =
        box.load(YamlContext.YAML_LOAD, AssertionsBox.AssertionsYaml::class.java, "assertions")
            ?: AssertionsBox.AssertionsYaml()


    override val assertions: AssertionsBox = AssertionsBox(assertionsYaml) { storeAssertions() }

    override val baseService: SuuRestService
        get() {
            if (yaml.baseService == null) {
                yaml.baseService = RestServiceBox.RestServiceYaml()
            }
            return RestServiceLocalBox(yaml.baseService!!) { store() }
        }

    override var baseResource: List<SuuRestResource>
        get() {
            TODO("not implemented")
        } //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override val baseMethod: SuuRestMethod
        get() {
            if (yaml.baseMethod == null) {
                yaml.baseMethod = RestMethodBox.RestMethodYaml()
            }
            return RestMethodLocalBox(yaml.baseMethod!!) { store() }
        }


    override val request: SuuRestRequest
        get() {
            if (yaml.request == null) {
                yaml.request = RestRequestBox.RestRequestYaml()
            }
            return RestRequestLocalBox(box, yaml.request!!) { store() }
        }



    override fun store() {
        box.write(yaml)
    }

    fun storeAssertions() {
        box.write(assertionsYaml, "assertions")
    }

    class RestRequestYaml : TestStepYaml() {
        var baseService: RestServiceBox.RestServiceYaml? = null
        var baseResource: MutableList<RestResourceBox.RestResourceYaml>? = ArrayList()
        var baseMethod: RestMethodBox.RestMethodYaml? = null
        var request: RestRequestBox.RestRequestYaml? = null
    }


    class RestServiceLocalBox(
        private val yaml: RestServiceBox.RestServiceYaml,
        private val store: () -> Unit
    ) : SuuRestService {

        override var name: String?
            get() = yaml.name
            set(value) {
                if (yaml.name != value) {
                    yaml.name = value
                    store()
                }
            }

        override var description: String?
            get() = yaml.description
            set(value) {
                if (yaml.description != value) {
                    yaml.description = value
                    store()
                }
            }

        override var basePath: String?
            get() = yaml.basePath
            set(value) {
                yaml.basePath = value
                store()
            }

        override val resources: List<SuuRestResource> by lazy { throw UnsupportedOperationException() }

        override fun createResource(name: String, path: String): SuuRestResource =
            throw UnsupportedOperationException("")

    }

    class RestResourceLocalBox(
        private val yaml: RestResourceBox.RestResourceYaml,
        private val store: () -> Unit
    ) : SuuRestResource {
        override val methods: List<SuuRestMethod> by lazy { throw UnsupportedOperationException("Not supported in TestStep") }
        override val childResources: List<SuuRestResource>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

        override fun createMethod(name: String): SuuRestMethod =
            throw UnsupportedOperationException("Not supported in TestStep")


        override fun createChildResource(name: String, path: String): SuuRestResource {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override var name: String
            get() = yaml.name ?: ""
            set(value) {
                if (yaml.name != value) {
                    yaml.name = value
                    store()
                }
            }

        override var description: String?
            get() = yaml.description
            set(value) {
                if (yaml.description != value) {
                    yaml.description = value
                    store()
                }
            }

        override var path: String?
            get() = yaml.path ?: ""
            set(value) {
                if (yaml.path != value) {
                    yaml.path = value
                    store()
                }
            }

        override val parameters: SuuRestParameters
            get() = RestParameters(yaml.parameters!!) { store() }
    }

    class RestMethodLocalBox(
        private val yaml: RestMethodBox.RestMethodYaml,
        private val store: () -> Unit
    ) : SuuRestMethod {
        override var name: String
            get() = yaml.name ?: ""
            set(value) {
                if (yaml.name != value) {
                    yaml.name = value
                    store()
                }
            }

        override var description: String?
            get() = yaml.description
            set(value) {
                if (yaml.description != value) {
                    yaml.description = value
                    store()
                }
            }


        override var httpMethod: SuuRestMethod.HttpMethod?
            get() = yaml.httpMethod
            set(value) {
                if (yaml.httpMethod != value) {
                    yaml.httpMethod = value
                    store()
                }
            }

        override val parameters: SuuRestParameters
            get() = RestParameters(yaml.parameters!!) { store() }


        override val requests: List<SuuRestRequest> by lazy { throw UnsupportedOperationException() }

        override fun createRequest(name: String): SuuRestRequest {
            throw UnsupportedOperationException()
        }

    }

    class RestRequestLocalBox(
        private val box: Box,
        private val yaml: RestRequestBox.RestRequestYaml,
        private val store: () -> Unit
    ) : SuuRestRequest {
        override var name: String
            get() = yaml.name ?: ""
            set(value) {
                if (yaml.name != value) {
                    yaml.name = value
                    store()
                }
            }

        override var description: String?
            get() = yaml.description
            set(value) {
                if (yaml.description != value) {
                    yaml.description = value
                    store()
                }
            }


        override val parameters: SuuRestParameters
            get() = RestParameters(yaml.parameters!!) { store() }

        override val headers: List<SuuRestRequest.Header>
            get() = yaml.headers?.map { RestRequestBox.mapHeader(it) } ?: ArrayList()

        override fun removeHeader(key: String) {
            val changed = yaml.headers?.removeIf { it.key == key } ?: false
            if (changed) {
                store()
            }
        }

        override var content: String?
            get() = box.loadSection("content")
            set(value) {
                if (content != value) {
                    box.writeSection("content", value)
                }
            }


        override fun addOrUpdateHeader(header: SuuRestRequest.Header) {
            RestRequestBox.handleHeaders(yaml, header) { store() }
        }

    }

    companion object {
        fun create(parent: Box, name: String): TestStepRestRequestBox {
            return TestStepRestRequestBox(createBox(parent, name, TestStepRestRequestBox.RestRequestYaml()))

        }

    }

}