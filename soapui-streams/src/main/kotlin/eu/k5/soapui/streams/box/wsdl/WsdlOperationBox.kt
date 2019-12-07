package eu.k5.soapui.streams.box.wsdl

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.HeaderBox
import eu.k5.soapui.streams.box.rest.RestRequestBox
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class WsdlOperationBox(
    private val box: Box
) : SuuWsdlOperation {
    override fun markLostAndFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val yaml: WsdlOperationYaml = box.load(WsdlOperationYaml::class.java)

    override val name: String
        get() = yaml.name ?: ""

    override var description: String?
        get() = yaml.description
        set(value) {
            if (BoxImpl.changed(yaml.description, value)) {
                yaml.description = value
                store()
            }
        }
    override val requests: MutableList<WsdlRequestBox> by lazy {
        box.findFolderBox { it.fileName.toString() != FILE_NAME }
            .map { WsdlRequestBox(it) }.toMutableList()
    }

    override fun createRequest(name: String): WsdlRequestBox {
        val init = requests
        val newRequest = WsdlRequestBox.create(box, name)
        init.add(newRequest)
        return newRequest
    }


    private fun store() {
        box.write(WsdlOperationYaml::class.java, yaml)
    }

    class WsdlOperationYaml {
        var name: String? = null
        var description: String? = null
    }

    companion object {
        fun create(parentBox: Box, name: String): WsdlOperationBox {
            val box = parentBox.createFolder(name, FILE_NAME)
            val wsdlOperationYaml = WsdlOperationYaml()
            wsdlOperationYaml.name = name
            box.write(WsdlOperationYaml::class.java, wsdlOperationYaml)
            return WsdlOperationBox(box)
        }

        const val FILE_NAME = "operation.box.yml"
    }
}