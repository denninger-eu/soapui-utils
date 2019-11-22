package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.test.SuuAssertionListener

class CopyAssertionListener(
    private val target: SuuAssertions
) : SuuAssertionListener {


    override fun validStatus(assertion: SuuAssertionValidStatus) {
        val newAssertion = target.createValidStatus(assertion.name)
        newAssertion.statusCodes = assertion.statusCodes
    }

    override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
        val newAssertion = target.createInvalidStatus(assertion.name)
        newAssertion.statusCodes = assertion.statusCodes
    }

    override fun contains(assertion: SuuAssertionContains) {
        val newAssertion = target.createContains(assertion.name)
        newAssertion.content = assertion.content
        newAssertion.regexp = assertion.regexp
        newAssertion.caseSensitive = assertion.caseSensitive
    }

    override fun notContains(assertion: SuuAssertionNotContains) {
        val newAssertion = target.createNotContains(assertion.name)
        newAssertion.content = assertion.content
        newAssertion.regexp = assertion.regexp
        newAssertion.caseSensitive = assertion.caseSensitive
    }

    override fun script(assertion: SuuAssertionScript) {
        val newAssertion = target.createScript(assertion.name)
        newAssertion.script = assertion.script
    }

    override fun duration(assertion: SuuAssertionDuration) {
        val newAssertion = target.createDuration(assertion.name)
        newAssertion.time = assertion.time
    }

    private fun <T : SuuAssertionJsonPath> handleJsonPath(source: T, target: T): T {
        target.enabled = source.enabled
        target.expression = source.expression
        target.expectedContent = source.expectedContent
        return target
    }

    override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
        handleJsonPath(assertion, target.createJsonPathCount(assertion.name))
    }

    override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
        handleJsonPath(assertion, target.createJsonPathExists(assertion.name))
    }

    override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
        handleJsonPath(assertion, target.createJsonPathMatch(assertion.name))
    }

    override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
        val newAssertion = handleJsonPath(assertion, target.createJsonPathRegEx(assertion.name))
        newAssertion.regularExpression = assertion.regularExpression
    }


    private fun <T : SuuAssertionXmlContains> handleXmlContains(source: T, target: T) {
        target.enabled = source.enabled
        target.expression = source.expression
        target.expectedContent = source.expectedContent
        target.allowWildcards = source.allowWildcards
        target.ignoreComments = source.ignoreComments
        target.ignoreNamespaceDifferences = source.ignoreNamespaceDifferences
    }

    override fun xpath(assertion: SuuAssertionXPath) {
        handleXmlContains(assertion, target.createXPath(assertion.name))
    }

    override fun xquery(assertion: SuuAssertionXQuery) {
        handleXmlContains(assertion, target.createXQuery(assertion.name))
    }


    override fun exitAssertions(assertions: SuuAssertions) {
    }
}

