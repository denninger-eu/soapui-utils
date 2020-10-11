package eu.k5.soapui.transform.extensions

import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest

fun SuuTestStepRestRequest.createUrl(baseUrl: String): String {
    var path = baseUrl
    for (resource in this.baseResources) {
        if (!(path.endsWith("/") || resource.path!!.startsWith("/"))) {
            path = "$path/"
        }
        path += resource.path
    }

    for (parameter in this.allParameters()) {
        if (parameter.value.style == SuuRestParameter.Style.TEMPLATE) {
            path = path.replace("{" + parameter.value.name + "}", "\${" + parameter.value.name + "}")
        }
    }
    return path
}