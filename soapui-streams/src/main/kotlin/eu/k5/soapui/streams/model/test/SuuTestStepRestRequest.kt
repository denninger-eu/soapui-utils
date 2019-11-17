package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService

interface SuuTestStepRestRequest : SuuTestStep {

    val baseService: SuuRestService
    val baseResource: List<SuuRestResource>
    val baseMethod: SuuRestMethod

    val request: SuuRestRequest

    val assertions: SuuAssertions


}