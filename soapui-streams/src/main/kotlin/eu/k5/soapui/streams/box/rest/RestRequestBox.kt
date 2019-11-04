package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestBox(
    val box: Box
) : SuuRestRequest {


    private val restRequest by lazy { box.load(RestRequestYaml::class.java) }

    override var name
        get() = restRequest.name
        set(value) {
            restRequest.name = value
            store()
        }

    override var description
        get() = restRequest.description
        set(value) {
            restRequest.description = value
            store()
        }

    override var content
        get() = box.loadSection("content")
        set(value) = storeContent(value)

    override val parameters: MutableList<RestParameter>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    class RestRequestYaml {
        var name: String? = null
        var description: String? = null
    }

    private fun store() {
        box.write(RestRequestYaml::class.java, restRequest)
    }

    private fun storeContent(content: String?) {
        box.writeSection("content", content)
    }

    companion object {

        fun create(parent: Box, name: String): RestRequestBox {
            val box = parent.createFile(name, ".box.yaml")

            val newRequest = RestRequestYaml()
            newRequest.name = name
            box.write(RestRequestYaml::class.java, newRequest)
            return RestRequestBox(box)
        }

    }
}