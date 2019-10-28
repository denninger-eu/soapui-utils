package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.SuuRestService

interface SuProject {
    var name: String

    fun addRestService(restService: SuuRestService)
}
