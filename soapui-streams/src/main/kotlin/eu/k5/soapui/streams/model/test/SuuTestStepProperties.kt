package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.SuuProperties

interface SuuTestStepProperties : SuuTestStep {
    val properties: SuuProperties
}