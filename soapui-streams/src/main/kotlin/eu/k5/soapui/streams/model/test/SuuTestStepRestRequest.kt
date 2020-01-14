package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.*

interface SuuTestStepRestRequest : SuuTestStep {

    val baseService: SuuRestService
    val baseResources: List<SuuRestResource>
    val baseMethod: SuuRestMethod

    var requestPath: RequestPath

    val request: SuuRestRequest

    val assertions: SuuAssertions


    class RequestPath(
        val restService: String,
        val resources: List<String>,
        val method: String
    )


    fun allParameters(): Map<String, SuuRestParameter> {
        val allParameters = HashMap<String, SuuRestParameter>()
        for (resource in baseResources) {
            for (parameter in resource.parameters.allParameters) {
                allParameters[parameter.name] = parameter
            }
        }
        for (parameter in baseMethod.parameters.allParameters) {
            allParameters[parameter.name] = parameter
        }
        for (parameter in request.parameters.allParameters) {
            allParameters[parameter.name] = parameter
        }
        return allParameters
    }
}