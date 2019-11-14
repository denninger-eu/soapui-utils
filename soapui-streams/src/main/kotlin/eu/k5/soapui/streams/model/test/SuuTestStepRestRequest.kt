package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestRequest

interface SuuTestStepRestRequest : SuuTestStep {

    var request: SuuRestRequest

    val assertions: SuuAssertions



}