package eu.k5.soapui.streams.model.assertion

interface SuuAssertions {

    val assertions: List<SuuAssertion>


    fun getAssertion(name: String): SuuAssertion? {
        return assertions.firstOrNull { it.name == name }
    }
}