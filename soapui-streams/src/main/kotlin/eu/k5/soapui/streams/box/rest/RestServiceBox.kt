package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.Box.Companion.changed
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService

class RestServiceBox(
    private val box: Box

) : SuuRestService {
    private val restService: RestServiceYaml by lazy { box.load(RestServiceYaml::class.java) }


    override var name: String
        get() = restService.name ?: ""
        set(value) {
            if (changed(restService.name, value)) {
                restService.name = value
                store()
            }
        }

    override var description: String?
        get() = restService.description
        set(value) {
            if (changed(restService.description, value)) {
                restService.description = value
                store()
            }
        }

    override var basePath: String
        get() = restService.basePath ?: ""
        set(value) {
            if (changed(restService.basePath, value)) {
                restService.basePath = value
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
    }

    fun store() {
        box.write(RestServiceYaml::class.java, restService)
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