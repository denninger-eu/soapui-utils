package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.transform.restassured.model.Scenario

interface Transformer<T> {
    fun transform(step: T)
}