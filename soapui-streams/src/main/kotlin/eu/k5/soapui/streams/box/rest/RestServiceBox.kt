package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService

class RestServiceBox(
    private val box: Box

) : SuuRestService {

    private val yaml: RestServiceYaml by lazy { box.load(RestServiceYaml::class.java) }


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

    override val endpoints: List<String>
        get() = yaml.endpoints ?: ArrayList()

    override fun addEndpoint(endpoint: String) {
        if (yaml.endpoints == null) {
            yaml.endpoints = ArrayList()
        }
        if (!yaml.endpoints!!.contains(endpoint)) {
            yaml.endpoints!!.add(endpoint)
            store()
        }
    }

    override fun removeEndpoint(endpoint: String) {
        if (yaml.endpoints == null) {
            return
        }
        if (yaml.endpoints!!.contains(endpoint)) {
            yaml.endpoints!!.remove(endpoint)
            store()
        }
    }


    override val resources by lazy {
        box.findSubFolderBox { it.fileName.toString() == RestResourceBox.FILE_NAME }
            .map { RestResourceBox(it) }
            .toMutableList()
    }

    override fun createResource(name: String, path: String): SuuRestResource {
        val init = resources
        val newRestService = RestResourceBox.create(box, name, path)
        init.add(newRestService)
        return newRestService
    }

    class RestServiceYaml {
        var name: String? = null
        var description: String? = null
        var basePath: String? = null
        var endpoints: MutableList<String>? = ArrayList()
    }

    fun store() {
        box.write(RestServiceYaml::class.java, yaml)
    }

    companion object {
        fun create(parentBox: Box, name: String): RestServiceBox {
            val box = parentBox.createFolder(name, FILE_NAME)
            val restService = RestServiceYaml()
            restService.name = name
            box.write(RestServiceYaml::class.java, restService)
            return RestServiceBox(box)
        }

        const val FILE_NAME = "restservice.box.yaml"
    }

}