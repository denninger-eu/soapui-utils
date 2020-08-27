package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.WithHeader

interface SuuRestRequest : WithHeader {

    var name: String

    var description: String?

    val parameters: SuuRestParameters

    var content: String?

    var mediaType: String?


    override val additionalHeaders: List<Header>
        get() = if (mediaType != null) {
            listOf(Header("Content-Type", mediaType!!))
        } else {
            emptyList()
        }
}