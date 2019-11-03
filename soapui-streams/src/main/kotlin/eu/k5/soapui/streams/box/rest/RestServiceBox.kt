package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.ProjectBox
import eu.k5.soapui.streams.jaxb.rest.RestResource
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestService

class RestServiceBox(
    val box: Box
) : SuuRestService {

    private val restService: RestServiceYaml
        get() = box.load(RestServiceYaml::class.java)

    override var name: String? = restService.name
    override var description: String? = restService.description
    override var basePath: String? = restService.basePath

    override val resources: List<SuuResource>
        get() = box.findFolderBox { it.fileName.toString() == "resource.box.yaml" }.map { RestResourceBox(it) }

    override fun createResource(name: String, path: String): SuuResource {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class RestServiceYaml {
        var name: String? = null
        var description: String? = null
        var basePath: String? = null
    }
}