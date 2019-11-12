package eu.k5.soapui.streams.model.test

interface SuuTestCase {

    var name: String
    var enabled: Boolean

    val steps: List<SuuTestStep>

    fun getStep(name: String) = steps.firstOrNull { it.name == name }


}
