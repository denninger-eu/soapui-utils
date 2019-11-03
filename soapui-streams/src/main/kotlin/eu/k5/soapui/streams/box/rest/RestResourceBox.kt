package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod

class RestResourceBox(
    private val box: Box
) : SuuResource {
    private val resource: RestResourceYaml
        get() = box.load(RestResourceYaml::class.java)

    override var name: String? = resource.name
    override var description: String? = resource.description
    override var path: String? = resource.path


    override val parameters: MutableList<RestParameter>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val methods: List<SuuRestMethod>
        get() = box.findFolderBox { it.fileName.toString() == "method.box.yaml" }.map { RestMethodBox(it) }
    override val resources: List<SuuResource>
        get() = box.findFolderBox { it.fileName.toString() == "resource.box.yaml" }.map { RestResourceBox(it) }

    override fun createMethod(name: String): SuuRestMethod {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createChildResource(name: String, path: String): SuuResource {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class RestResourceYaml {
        var name: String? = null
        var description: String? = null
        var path: String? = null
    }
}