package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.test.SuuAssertionListener


class DifferenceAssertionListener(
    private val differences: Differences,
    private val assertions: SuuAssertions
) : SuuAssertionListener {


    override fun validStatus(assertion: SuuAssertionValidStatus) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionValidStatus) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.Type.ASSERTION, assertion.name)
        differences.addChange("statusCodes", ref.statusCodes, assertion.statusCodes)
    }

    override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionInvalidStatus) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.Type.ASSERTION, assertion.name)
        differences.addChange("statusCodes", ref.statusCodes, assertion.statusCodes)
    }

    override fun contains(assertion: SuuAssertionContains) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionContains) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.Type.ASSERTION, assertion.name)
        differences.addChange("content", ref.content, assertion.content)
        differences.addChange("caseSensitive", ref.caseSensitive, assertion.caseSensitive)
        differences.addChange("regexp", ref.regexp, assertion.regexp)
    }

    override fun notContains(assertion: SuuAssertionNotContains) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionNotContains) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.Type.ASSERTION, assertion.name)
        differences.addChange("content", ref.content, assertion.content)
        differences.addChange("caseSensitive", ref.caseSensitive, assertion.caseSensitive)
        differences.addChange("regexp", ref.regexp, assertion.regexp)
    }

    override fun script(assertion: SuuAssertionScript) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionScript) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.Type.ASSERTION, assertion.name)
        differences.addChange("script", ref.script, assertion.script)
    }

    override fun duration(assertion: SuuAssertionDuration) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionDuration) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.Type.ASSERTION, assertion.name)
        differences.addChange("time", ref.time, assertion.time)
    }

    private fun <T : SuuAssertionJsonPath> handleJsonPath(ref: SuuAssertion?, assertion: T): T? {
        if (ref !is SuuAssertionJsonPath) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return null
        }
        differences.addChange("enabled", ref.enabled, assertion.enabled)
        differences.addChange("expectedContent", ref.expectedContent, assertion.expectedContent)
        differences.addChange("expression", ref.expression, assertion.expression)
        return ref as T?
    }

    override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
        handleJsonPath(assertions.getAssertion(assertion.name), assertion)
    }

    override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
        handleJsonPath(assertions.getAssertion(assertion.name), assertion)
    }

    override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
        handleJsonPath(assertions.getAssertion(assertion.name), assertion)
    }

    override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
        val ref = handleJsonPath(assertions.getAssertion(assertion.name), assertion)
        differences.addChange("regularExpression", ref?.regularExpression, assertion.regularExpression)
    }

    private fun <T : SuuAssertionXmlContains> handleXmlContains(ref: SuuAssertion?, assertion: T): T? {
        if (ref !is SuuAssertionXmlContains) {
            differences.addChange(Differences.Type.ASSERTION, assertion.name)
            return null
        }
        differences.addChange("enabled", ref.enabled, assertion.enabled)
        differences.addChange("expectedContent", ref.expectedContent, assertion.expectedContent)
        differences.addChange("expression", ref.expression, assertion.expression)
        differences.addChange("allowWildcards", ref.allowWildcards, assertion.allowWildcards)
        differences.addChange("ignoreComments", ref.ignoreComments, assertion.ignoreComments)
        differences.addChange(
            "ignoreNamespaceDifferences",
            ref.ignoreNamespaceDifferences,
            assertion.ignoreNamespaceDifferences
        )
        return ref as T?
    }

    override fun xpath(assertion: SuuAssertionXPath) {
        handleXmlContains(assertions.getAssertion(assertion.name), assertion)
    }

    override fun xquery(assertion: SuuAssertionXQuery) {
        handleXmlContains(assertions.getAssertion(assertion.name), assertion)
    }

    override fun exitAssertions(assertions: SuuAssertions) {


    }
}