package eu.k5.soapui.streams.model.assertion

interface SuuAssertionJsonPath : SuuAssertion {
    var expression: String?
    var expectedContent: String?
}