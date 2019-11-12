package eu.k5.soapui.streams.model.test

interface SuuTestSuite {

    var name: String

    var enabled: Boolean

    val testCases: List<SuuTestCase>

    fun getTestCase(name: String): SuuTestCase?= testCases.firstOrNull { it.name == name }

}