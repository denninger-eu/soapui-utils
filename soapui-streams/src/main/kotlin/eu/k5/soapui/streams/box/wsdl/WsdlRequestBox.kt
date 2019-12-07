package eu.k5.soapui.streams.box.wsdl

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.HeaderBox
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class WsdlRequestBox(
    private val box: Box
) : SuuWsdlRequest {
    override fun markLostAndFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val yaml: WsdlRequestYaml = box.load(WsdlRequestYaml::class.java)
    override var name: String
        get() = yaml.name ?: ""
        set(value) {
            if (BoxImpl.changed(yaml.name, value)) {
                yaml.name = value
                store()
            }
        }

    override var description: String?
        get() = yaml.description ?: ""
        set(value) {
            if (BoxImpl.changed(yaml.description, value)) {
                yaml.description = value
                store()
            }
        }
    override var content: String
        get() = box.loadSection("content") ?: ""
        set(value) {
            box.writeSection("content", value)
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


    private fun store() {
        box.write(WsdlRequestYaml::class.java, yaml)
    }

    class WsdlRequestYaml : HeaderBox.WithHeaderYaml {
        var name: String? = null
        var description: String? = null
        override var headers: MutableList<HeaderBox.HeaderYaml>? = ArrayList()
    }

    companion object {
        fun create(parent: Box, name: String): WsdlRequestBox {
            val box = parent.createFile(name, ".box.yaml")
            val wsdlRequestYaml = WsdlRequestYaml()
            wsdlRequestYaml.name = name
            box.write(WsdlRequestYaml::class.java, wsdlRequestYaml)
            return WsdlRequestBox(box)
        }
    }
}