package eu.k5.soapui.streams.model.assertion

interface SuuAssertionXmlContains : SuuAssertion {
    var expression: String?
    var expectedContent: String?
    var allowWildcards: Boolean
    var ignoreNamespaceDifferences: Boolean
    var ignoreComments: Boolean
}