package eu.k5.soapui.streams.box.wsdl

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation
import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.box.test.TestSuiteBox
import eu.k5.soapui.streams.model.wsdl.SuuWsdlDefinition
import eu.k5.soapui.streams.model.wsdl.SuuWsdlDefinitionPart
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService

class WsdlServiceBox(
    private val box: Box
) : SuuWsdlService {
    override fun markLostAndFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val yaml: WsdlServiceYaml by lazy { box.load(WsdlServiceYaml::class.java) }

    override var name: String
        get() = yaml.name ?: ""
        set(value) {
            if (BoxImpl.changed(yaml.name, value)) {
                yaml.name = value
                store()
            }
        }

    override var description: String?
        get() = yaml.description
        set(value) {
            if (BoxImpl.changed(yaml.description, value)) {
                yaml.description = value
                store()
            }
        }

    override val operations: MutableList<WsdlOperationBox> by lazy {
        box.findSubFolderBox { it.fileName.toString() == WsdlOperationBox.FILE_NAME }.map { WsdlOperationBox(it) }
            .toMutableList()
    }
    override val definition: SuuWsdlDefinition
        get() = TODO("Not yet implemented")

    override fun createOperation(name: String): SuuWsdlOperation {
        val init = operations
        val newTestSuite = WsdlOperationBox.create(box, name)
        init.add(newTestSuite)
        return newTestSuite
    }

    class WsdlServiceYaml {
        var name: String? = null
        var description: String? = null
        var basePath: String? = null
        var endpoints: MutableList<String>? = ArrayList()
    }

    private fun store() {
        box.write(yaml)
    }

    companion object {
        fun create(parentBox: Box, name: String): WsdlServiceBox {
            val box = parentBox.createFolder(name, FILE_NAME)
            val wsdlServiceYaml = WsdlServiceYaml()
            wsdlServiceYaml.name = name
            box.write(WsdlServiceYaml::class.java, wsdlServiceYaml)
            return WsdlServiceBox(box)
        }

        const val FILE_NAME = "wsdlservice.box.yaml"
    }
}