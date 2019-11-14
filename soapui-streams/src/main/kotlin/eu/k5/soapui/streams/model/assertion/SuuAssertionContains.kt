package eu.k5.soapui.streams.model.assertion

interface SuuAssertionContains : SuuAssertion {

    var content: String?
    var regexp: Boolean
    var caseSensitive: Boolean

}