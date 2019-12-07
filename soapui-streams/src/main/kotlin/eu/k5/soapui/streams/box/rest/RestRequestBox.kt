package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.box.HeaderBox
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestBox(
    private val box: Box,
    private val method: RestMethodBox
) : SuuRestRequest {


    private val yaml by lazy { box.load(RestRequestYaml::class.java) }

    override var name
        get() = yaml.name ?: ""
        set(value) {
            if (changed(yaml.name, value)) {
                yaml.name = value
                store()
            }
        }

    override var description
        get() = yaml.description
        set(value) {
            if (changed(yaml.description, value)) {
                yaml.description = value
                store()
            }
        }


    override val parameters: SuuRestParameters by lazy {
        RestParametersBox(
            yaml.parameters!!,
            true,
            method.parameters
        ) { store() }
    }

    override val headers: List<Header>
        get() = yaml.headers?.map { HeaderBox.mapHeader(it) } ?: ArrayList()

    override fun removeHeader(key: String) {
        val changed = yaml.headers?.removeIf { it.key == key } ?: false
        if (changed) {
            store()
        }
    }

    override fun addOrUpdateHeader(header: Header) {
        HeaderBox.handleHeaders(yaml, header) { store() }
    }

    override var content
        get() = box.loadSection("content")
        set(value) = storeContent(value)

    class RestRequestYaml : HeaderBox.WithHeaderYaml {
        var name: String? = null
        var description: String? = null
        var parameters: MutableList<RestParametersBox.RestParameterYaml>? = ArrayList()
        override var headers: MutableList<HeaderBox.HeaderYaml>? = ArrayList()
    }


    private fun store() {
        box.write(RestRequestYaml::class.java, yaml)
    }

    private fun storeContent(content: String?) {
        box.writeSection("content", content)
    }

    companion object {

        fun create(parent: RestMethodBox, name: String): RestRequestBox {
            val box = parent.box.createFile(name, ".box.yaml")

            val newRequest = RestRequestYaml()
            newRequest.name = name
            box.write(RestRequestYaml::class.java, newRequest)
            return RestRequestBox(box, parent)
        }


    }
}