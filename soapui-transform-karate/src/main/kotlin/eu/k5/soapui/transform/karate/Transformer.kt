package eu.k5.soapui.transform.karate

import eu.k5.soapui.transform.karate.model.Scenario
import eu.k5.soapui.transform.karate.model.Statement

interface Transformer<T> {

    fun transform(scenario: Scenario, step: T)


}