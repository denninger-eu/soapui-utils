package eu.k5.soapui.transform.karate

import eu.k5.soapui.transform.karate.model.literals.VariableLiteral

class Environment {

    private var featureVariable: Int = 0
    fun getTempFeatureVariable(): VariableLiteral {
        return VariableLiteral("t" + featureVariable++)
    }


    fun addFile(content: String) {

    }

    val contextClassName = "eu.k5.dread.karate.RunnerContext"

    val createPropertiesStep = "propertiesStep"

    val createRequestStep = "requestStep"

    val expandProperties = "expand"
}