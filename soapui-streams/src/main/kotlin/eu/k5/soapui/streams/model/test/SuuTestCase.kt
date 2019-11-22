package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService

interface SuuTestCase {

    var name: String

    var enabled: Boolean

    val properties: SuuProperties

    val steps: List<SuuTestStep>

    fun getStep(name: String) = steps.firstOrNull { it.name == name }

    fun <T : SuuTestStep> createStep(name: String, java: Class<T>): T

    fun createRestRequestStep(
        name: String,
        restService: SuuRestService,
        restResources: List<SuuRestResource>,
        restMethod: SuuRestMethod
    ): SuuTestStepRestRequest

    fun createScriptStep(name: String) : SuuTestStepScript
}
