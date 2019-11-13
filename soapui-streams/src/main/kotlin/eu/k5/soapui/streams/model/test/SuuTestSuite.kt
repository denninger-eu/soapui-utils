package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.SuuProperties

interface SuuTestSuite {

    var name: String

    var enabled: Boolean

    val properties: SuuProperties

    val testCases: List<SuuTestCase>

    fun getTestCase(name: String): SuuTestCase? = testCases.firstOrNull { it.name == name }

    fun createTestCase(name: String): SuuTestCase

}