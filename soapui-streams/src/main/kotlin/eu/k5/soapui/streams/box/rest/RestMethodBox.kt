package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestMethodBox(
    private val box: Box
) : SuuRestMethod {
    private val method by lazy { box.load(RestMethodYaml::class.java) }

    override var name
        get() = method.name
        set(value) {
            method.name = value
            store()
        }
    override var description
        get() = method.description
        set(value) {
            method.description = value
            store()
        }
    override var httpMethod
        get() = method.httpMethod
        set(value) {
            method.httpMethod = value
            store()
        }

    override val parameters: MutableList<RestParameter>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val requests by lazy {
        box.findFolderBox { it.fileName.toString() != "method.box.yaml" }
            .map { RestRequestBox(it) }
            .toMutableList()
    }

    override fun createRequest(name: String): SuuRestRequest {
        val init = requests
        val newRequest = RestRequestBox.create(box, name)
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
    }

    companion object {
        fun create(parentBox: Box, name: String): RestMethodBox {
            val box = parentBox.createFolder(name, RestMethodBox.FILE_NAME)
            val method = RestMethodYaml()
            method.name = name
            box.write(RestMethodYaml::class.java, method)
            return RestMethodBox(box)
        }

        const val FILE_NAME = "method.box.yaml"
    }
}