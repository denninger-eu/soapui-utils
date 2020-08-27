package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.model.rest.SuuRestRequest

class DifferenceRestRequest(
    private val differences: Differences
) {
    private val misc = DifferenceMisc(differences)
    fun handle(reference: SuuRestRequest?, actual: SuuRestRequest) {
        if (reference == null) {
            differences.addAdditional(actual.name)
            return
        }
        differences.pushRequest(actual.name)
        differences.addChange("description", reference.description, actual.description)
        differences.addChange("content", reference.content?.trim(), actual.content?.trim())
        differences.addChange("mediaType", reference.mediaType, actual.mediaType)

        misc.handleParameters(reference.parameters, actual.parameters)

        misc.handleHeaders(reference.headers, actual.headers)
        differences.pop()

    }
}