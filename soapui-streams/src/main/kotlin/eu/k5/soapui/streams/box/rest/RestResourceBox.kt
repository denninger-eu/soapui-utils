package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class RestResourceBox(
    val box: Box,
    private val parentResource: RestResourceBox?
) : SuuRestResource {

    private val yaml by lazy { box.load(RestResourceYaml::class.java) }

    override val parent: SuuRestResource?
        get() = parentResource

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
    override var path: String?
        get() = yaml.path
        set(value) {
            if (changed(yaml.path, value)) {
                yaml.path = value
                store()
            }
        }


    override val parameters: RestParametersBox by lazy {
        RestParametersBox(
            yaml.parameters!!,
            false,
            parentResource?.parameters
        ) { store() }
    }

    override val methods by lazy {
        box.findSubFolderBox { it.fileName.toString() == RestMethodBox.FILE_NAME }
            .map { RestMethodBox(it, this) }
            .toMutableList()
    }
    override val childResources by lazy {
        box.findSubFolderBox { it.fileName.toString() == FILE_NAME }
            .map { RestResourceBox(it, this) }
            .toMutableList()
    }

    override fun createMethod(name: String): SuuRestMethod {
        val init = methods
        val newRestMethod = RestMethodBox.create(this, name)
        init.add(newRestMethod)
        return newRestMethod
    }

    override fun createChildResource(name: String, path: String): SuuRestResource {
        val init = childResources
        val newRestService = create(box, this, name, path)
        init.add(newRestService)
        return newRestService
    }

    private fun store() {
        box.write(RestResourceYaml::class.java, yaml)
    }

    class RestResourceYaml {
        var name: String? = null
        var description: String? = null
        var path: String? = null
        var parameters: MutableList<RestParametersBox.RestParameterYaml>? = ArrayList()
    }

    companion object {
        const val FILE_NAME = "resource.box.yaml"


        fun create(parent: Box, parentResource: RestResourceBox?, name: String, path: String): RestResourceBox {
            val folder = parent.createFolder(name, FILE_NAME)
            val newResource = RestResourceYaml()
            newResource.name = name
            newResource.path = path
            folder.write(RestResourceYaml::class.java, newResource)
            return RestResourceBox(folder, parentResource)
        }

    }
}