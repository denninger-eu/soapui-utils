package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.jaxb.rest.RestResource
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class RestResourceBox(
    private val box: Box
) : SuuRestResource {

    private val resource by lazy { box.load(RestResourceYaml::class.java) }

    override var name: String
        get() = resource.name ?: ""
        set(value) {
            resource.name = name
            store()
        }
    override var description: String?
        get() = resource.description
        set(value) {
            resource.description = value
            store()
        }
    override var path: String?
        get() = resource.path
        set(value) {
            resource.path = value
            store()
        }


    override val parameters: SuuRestParameters by lazy { RestParameters(resource.parameters!!) { store() } }

    override val methods by lazy {
        box.findSubFolderBox { it.fileName.toString() == RestMethodBox.FILE_NAME }
            .map { RestMethodBox(it) }
            .toMutableList()
    }
    override val childResources by lazy {
        box.findSubFolderBox { it.fileName.toString() == FILE_NAME }
            .map { RestResourceBox(it) }
            .toMutableList()
    }

    override fun createMethod(name: String): SuuRestMethod {
        val init = methods
        val newRestMethod = RestMethodBox.create(box, name)
        init.add(newRestMethod)
        return newRestMethod
    }

    override fun createChildResource(name: String, path: String): SuuRestResource {
        return RestResource()
    }

    private fun store() {
        box.write(RestResourceYaml::class.java, resource)
    }

    class RestResourceYaml {
        var name: String? = null
        var description: String? = null
        var path: String? = null
        var parameters: MutableList<RestParameters.RestParameterYaml>? = ArrayList()
    }

    companion object {
        const val FILE_NAME = "resource.box.yaml"

        fun create(parent: Box, name: String, path: String): RestResourceBox {
            val folder = parent.createFolder(name, FILE_NAME)
            val newResource = RestResourceYaml()
            newResource.name = name
            newResource.path = path
            folder.write(RestResourceYaml::class.java, newResource)
            return RestResourceBox(folder)
        }

    }
}