package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestMethodBox(
    private val box: Box
) : SuuRestMethod {
    private val restMethod: RestMethodYaml
        get() = box.load(RestMethodYaml::class.java)

    override var name: String? = restMethod.name
    override var description: String? = restMethod.description
    override var method: SuuRestMethod.HttpMethod? = restMethod.httpMethod


    override val parameters: MutableList<RestParameter>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val requests: List<SuuRestRequest>
        get() = box.findFolderBox { it.fileName.toString() != "method.box.yaml" }.map { RestRequestBox(it) }

    override fun createRequest(name: String): SuuRestRequest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class RestMethodYaml {
        var name: String? = null
        var description: String? = null
        var httpMethod: SuuRestMethod.HttpMethod? = null
    }
}