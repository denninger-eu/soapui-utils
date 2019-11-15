package eu.k5.soapui.streams.model.assertion

import eu.k5.soapui.streams.box.test.AssertionsBox

interface SuuAssertions {

    val assertions: List<SuuAssertion>

    fun isEmpty() = assertions.isEmpty()

    fun getAssertion(name: String): SuuAssertion? {
        return assertions.firstOrNull { it.name == name }
    }

    fun createInvalidStatus(name: String): SuuAssertionInvalidStatus
    fun createValidStatus(name: String): SuuAssertionValidStatus
    fun createContains(name: String): SuuAssertionContains
    fun createNotContains(name: String): SuuAssertionNotContains
    fun createDuration(name: String): SuuAssertionDuration
    fun createScript(name: String): SuuAssertionScript

}