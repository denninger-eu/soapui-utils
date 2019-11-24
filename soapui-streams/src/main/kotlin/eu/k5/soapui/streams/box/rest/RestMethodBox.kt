package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestMethodBox(
    val box: Box,
    private val resource: RestResourceBox
) : SuuRestMethod {
    private val method by lazy { box.load(RestMethodYaml::class.java) }

    override var name
        get() = method.name ?: ""
        set(value) {
            if (changed(method.name, value)) {
                method.name = value
                store()
            }
        }
    override var description
        get() = method.description
        set(value) {
            if (changed(method.description, value)) {
                method.description = value
                store()
            }
        }
    override var httpMethod
        get() = method.httpMethod
        set(value) {
            if (method.httpMethod != value) {
                method.httpMethod = value
                store()
            }
        }

    override val parameters: RestParametersBox by lazy {
        RestParametersBox(
            method.parameters!!,
            false,
            resource.parameters
        ) { store() }
    }

    override val requests by lazy {
        box.findFolderBox { it.fileName.toString() != "method.box.yaml" }
            .map { RestRequestBox(it, this) }
            .toMutableList()
    }

    override fun createRequest(name: String): SuuRestRequest {
        val init = requests
        val newRequest = RestRequestBox.create(this, name)
        init.add(newRequest)
        return newRequest
    }

    private fun store() {
        box.write(RestMethodYaml::class.java, method)
    }

    class RestMethodYaml {
        var name: String? = null
        var description: String? = null
        var httpMethod: SuuRestMethod.HttpMethod? = null
        var parameters: MutableList<RestParametersBox.RestParameterYaml>? = ArrayList()
    }

    companion object {
        fun create(parent: RestResourceBox, name: String): RestMethodBox {
            val box = parent.box.createFolder("_" + name, RestMethodBox.FILE_NAME)
            val method = RestMethodYaml()
            method.name = name
            box.write(RestMethodYaml::class.java, method)
            return RestMethodBox(box, parent)
        }

        const val FILE_NAME = "method.box.yaml"
    }
}