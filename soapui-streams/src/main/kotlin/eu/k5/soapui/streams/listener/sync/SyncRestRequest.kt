package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.model.rest.SuuRestRequest

class SyncRestRequest {

    private val misc = SyncMisc()

    fun handle(target: SuuRestRequest, reference: SuuRestRequest) {
        target.description = reference.description
        target.content = reference.content
        target.mediaType = reference.mediaType
        misc.handleParameters(
            target.parameters,
            reference.parameters
        )

        misc.copyHeaders(reference, target)
    }

}