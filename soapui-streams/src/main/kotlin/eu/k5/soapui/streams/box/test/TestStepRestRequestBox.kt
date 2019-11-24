package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.box.rest.*
import eu.k5.soapui.streams.model.rest.*
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import java.lang.UnsupportedOperationException
import kotlin.collections.ArrayList

class TestStepRestRequestBox(
    private val box: Box,
    private val yaml: RestRequestYaml = box.load(
        RestRequestYaml::class.java
    ) ?: RestRequestYaml(),
    private val baseYaml: TestStepRestRequestBox.BaseYaml = box.load(
        BaseYaml::class.java,
        "base"
    ) ?: BaseYaml()
) : TestStepBox(yaml), SuuTestStepRestRequest {
    override var requestPath: SuuTestStepRestRequest.RequestPath
        get() = yaml.requestPath ?: SuuTestStepRestRequest.RequestPath("", emptyList<String>(), "")
        set(value) {
            if (modified(yaml.requestPath, value)) {
                yaml.requestPath = value
                store()
            }
        }


    private val assertionsYaml: AssertionsBox.AssertionsYaml =
        box.load(AssertionsBox.AssertionsYaml::class.java, "assertions")
            ?: AssertionsBox.AssertionsYaml()


    override val assertions: AssertionsBox = AssertionsBox(assertionsYaml) { storeAssertions() }

    override val baseService: SuuRestService
        get() {
            if (baseYaml.baseService == null) {
                baseYaml.baseService = RestServiceBox.RestServiceYaml()
            }
            return RestServiceLocalBox(baseYaml.baseService!!) { store() }
        }

    override val baseResources: List<RestResourceLocalBox>
        get() {
            if (baseYaml.baseResources == null) {
                baseYaml.baseResources = ArrayList()
            }
            val result = ArrayList<RestResourceLocalBox>()
            var parentResource: RestResourceLocalBox? = null
            for (baseResourceYaml in baseYaml.baseResources!!) {
                parentResource = RestResourceLocalBox(baseResourceYaml, parentResource)
                result.add(parentResource)
            }
            return result
        }

    override val baseMethod: RestMethodLocalBox
        get() {
            if (baseYaml.baseMethod == null) {
                baseYaml.baseMethod = RestMethodBox.RestMethodYaml()
            }
            return RestMethodLocalBox(baseYaml.baseMethod!!, baseResources.last()) { store() }
        }


    override val request: RestRequestLocalBox
        get() {
            if (yaml.request == null) {
                yaml.request = RestRequestBox.RestRequestYaml()
            }
            return RestRequestLocalBox(box, yaml.request!!, baseMethod) { store() }
        }

    fun setBaseResources(baseResources: List<SuuRestResource>) {
        baseYaml.baseResources = ArrayList()

        for (baseResource in baseResources) {
            val newResource = RestResourceBox.RestResourceYaml()
            newResource.name = baseResource.name
            newResource.path = baseResource.path
            newResource.description = baseResource.description
            for (parameter in baseResource.parameters.allParameters) {
                val newParameter = RestParametersBox.RestParameterYaml()
                newParameter.name = parameter.name
                newParameter.value = parameter.value
                newParameter.location = parameter.location
                newParameter.style = parameter.style
                newResource.parameters?.add(newParameter)
            }
            baseYaml.baseResources?.add(newResource)
        }
        storeBase()

    }

    override fun store() {
        box.write(yaml)
    }

    fun storeBase() {
        box.write(baseYaml, "base")
    }

    fun storeAssertions() {
        box.write(assertionsYaml, "assertions")
    }

    fun setBaseRestService(restService: SuuRestService) {
        val newRestService = RestServiceBox.RestServiceYaml()
        newRestService.name = restService.name
        newRestService.description = restService.description
        newRestService.basePath = restService.basePath
        baseYaml.baseService = newRestService
        storeBase()
    }

    fun setBaseMethod(restMethod: SuuRestMethod) {
        val newRestMethod = RestMethodBox.RestMethodYaml()
        newRestMethod.name = restMethod.name
        newRestMethod.description = restMethod.description
        newRestMethod.httpMethod = restMethod.httpMethod
        for (parameter in restMethod.parameters.allParameters) {
            val newParameter = RestParametersBox.RestParameterYaml()
            newParameter.name = parameter.name
            newParameter.value = parameter.value
            newParameter.location = parameter.location
            newParameter.style = parameter.style
            newRestMethod.parameters?.add(newParameter)
        }
        baseYaml.baseMethod = newRestMethod
        storeBase()
    }

/*    fun setBaseRequest(restRequest: SuuRestRequest) {
        val newRestRequest = RestRequestBox.RestRequestYaml()
        newRestRequest.name = restRequest.name
        newRestRequest.description = restRequest.description
        for (parameter in restRequest.parameters.parameters) {
            val newParameter = RestParameters.RestParameterYaml()
            newParameter.name = parameter.name
            newParameter.value = parameter.value
            newParameter.location = parameter.location
            newParameter.style = parameter.style
            newRestRequest.parameters?.add(newParameter)
        }
        for (header in restRequest.headers) {
            val newHeader = RestRequestBox.HeaderYaml()
            newHeader.key = header.key
            newHeader.values = ArrayList(header.value)
            newRestRequest.headers?.add(newHeader)
        }
      //  yaml.baseRequest = newRestRequest
        store()

                var baseService: RestServiceBox.RestServiceYaml? = null
        var baseResources: MutableList<RestResourceBox.RestResourceYaml>? = ArrayList()
        var baseMethod: RestMethodBox.RestMethodYaml? = null
    }*/

    class RestRequestYaml : TestStepYaml() {
        var request: RestRequestBox.RestRequestYaml? = null
        var requestPath: SuuTestStepRestRequest.RequestPath? = null
    }


    class BaseYaml {
        var baseService: RestServiceBox.RestServiceYaml? = null
        var baseResources: MutableList<RestResourceBox.RestResourceYaml>? = ArrayList()
        var baseMethod: RestMethodBox.RestMethodYaml? = null
    }


    class RestServiceLocalBox(
        private val yaml: RestServiceBox.RestServiceYaml,
        private val store: () -> Unit
    ) : SuuRestService {
        override val endpoints: List<String>
            get() = ArrayList()

        override fun addEndpoint(endpoint: String) {
            throw UnsupportedOperationException("Not supported in local copy")
        }

        override fun removeEndpoint(endpoint: String) {
            throw UnsupportedOperationException("Not supported in local copy")
        }

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

        override var basePath: String
            get() = yaml.basePath ?: ""
            set(value) {
                if (changed(yaml.basePath, value)) {
                    yaml.basePath = value
                    store()
                }
            }

        override val resources: List<SuuRestResource> by lazy { throw UnsupportedOperationException() }

        override fun createResource(name: String, path: String): SuuRestResource =
            throw UnsupportedOperationException("")

    }

    class RestResourceLocalBox(
        private val yaml: RestResourceBox.RestResourceYaml,
        private val parentResource: RestResourceLocalBox?
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
                throw UnsupportedOperationException("")
            }

        override var description: String?
            get() = yaml.description
            set(value) {
                throw UnsupportedOperationException()
            }

        override var path: String?
            get() = yaml.path ?: ""
            set(value) {
                throw UnsupportedOperationException()
            }

        override val parameters: RestParametersBox
            get() = RestParametersBox(
                yaml.parameters!!,
                false,
                parentResource?.parameters
            ) { throw UnsupportedOperationException() }
    }

    class RestMethodLocalBox(
        private val yaml: RestMethodBox.RestMethodYaml,
        private val resource: RestResourceLocalBox,
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

        override val parameters: RestParametersBox
            get() = RestParametersBox(yaml.parameters!!, false, resource.parameters) { store() }


        override val requests: List<SuuRestRequest> by lazy { throw UnsupportedOperationException() }

        override fun createRequest(name: String): SuuRestRequest {
            throw UnsupportedOperationException()
        }

    }

    class RestRequestLocalBox(
        private val box: Box,
        private val yaml: RestRequestBox.RestRequestYaml,
        private val method: RestMethodLocalBox,
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
            get() = RestParametersBox(yaml.parameters!!, true, method.parameters) { store() }

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

        private fun modified(
            existing: SuuTestStepRestRequest.RequestPath?,
            update: SuuTestStepRestRequest.RequestPath
        ): Boolean {
            if (existing == null) {
                return true
            }
            if (existing.restService != update.restService) {
                return true
            }
            if (existing.method != update.method) {
                return true
            }
            return existing.resources != update.resources
        }
    }

}