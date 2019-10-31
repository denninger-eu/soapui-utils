package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.visitor.listener.SuListener

interface SuProject {
    var name: String

    val restServices: List<SuuRestService>

    fun addRestService(restService: SuuRestService)

    fun getRestService(name: String): SuuRestService? = restServices.first { it.name == name }

    fun createRestService(name: String): SuuRestService

}
