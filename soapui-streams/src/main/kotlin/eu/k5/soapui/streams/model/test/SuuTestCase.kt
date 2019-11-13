package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.SuuProperties

interface SuuTestCase {

    var name: String

    var enabled: Boolean

    val properties: SuuProperties

    val steps: List<SuuTestStep>


    fun getStep(name: String) = steps.firstOrNull { it.name == name }
}
