package eu.k5.soapui.streams.model.rest

import javax.xml.bind.annotation.XmlElement

interface SuuRestService {
    var name: String?
    var description: String?
    var basePath: String?
    val resources: List<SuuResource>

    fun getResource(name: String): SuuResource? = resources.firstOrNull { it.name == name }

    fun createResource(name: String, path: String): SuuResource
}
