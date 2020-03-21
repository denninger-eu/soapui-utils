package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.test.SuuAssertionListener


class DifferenceAssertionListener(
    private val differences: Differences,
    private val assertions: SuuAssertions
) : SuuAssertionListener {
    override fun soapResponse(assertion: SuuAssertionSoapResponse) {
    }

    private inline fun <reified T : SuuAssertion> handle(
        assertion: T, check: (T) -> Unit
    ) {
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is T) {
            differences.addChange(Differences.EntityType.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.EntityType.ASSERTION, assertion.name)
        differences.addChange("enabled", ref.enabled, assertion.enabled)

        check(ref)
        differences.pop()
    }

    override fun validStatus(assertion: SuuAssertionValidStatus) {

        handle(assertion) {
            differences.addChange("statusCodes", it.statusCodes, assertion.statusCodes)
        }
/*
        val ref = assertions.getAssertion(assertion.name)
        if (ref !is SuuAssertionValidStatus) {
            differences.addChange(Differences.EntityType.ASSERTION, assertion.name)
            return
        }
        differences.push(Differences.EntityType.ASSERTION, assertion.name)
        differences.addChange("statusCodes", ref.statusCodes, assertion.statusCodes)
*/
    }

    override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
        handle(assertion) {
            differences.addChange("statusCodes", it.statusCodes, assertion.statusCodes)
        }
    }

    override fun contains(assertion: SuuAssertionContains) {
        handle(assertion) { ref ->
            differences.addChange("content", ref.content, assertion.content)
            differences.addChange("caseSensitive", ref.caseSensitive, assertion.caseSensitive)
            differences.addChange("regexp", ref.regexp, assertion.regexp)
        }
    }

    override fun notContains(assertion: SuuAssertionNotContains) {
        handle(assertion) { ref ->
            differences.addChange("content", ref.content, assertion.content)
            differences.addChange("caseSensitive", ref.caseSensitive, assertion.caseSensitive)
            differences.addChange("regexp", ref.regexp, assertion.regexp)
        }
    }

    override fun script(assertion: SuuAssertionScript) {
        handle(assertion) { ref ->
            differences.addChange("script", ref.script, assertion.script)
        }
    }

    override fun duration(assertion: SuuAssertionDuration) {
        handle(assertion) { ref ->
            differences.addChange("time", ref.time, assertion.time)
        }
    }

    private inline fun <reified T : SuuAssertionJsonPath> handleJsonPath(
        assertion: T, check: (T) -> Unit = {}
    ) {
        handle(assertion) { ref ->
            differences.addChange("expectedContent", ref.expectedContent, assertion.expectedContent)
            differences.addChange("expression", ref.expression, assertion.expression)
            check(ref)
        }
    }

    override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
        handleJsonPath(assertion)
    }

    override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
        handleJsonPath(assertion)
    }

    override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
        handleJsonPath(assertion)
    }

    override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
        val ref = handleJsonPath(assertion) { ref ->
            differences.addChange("regularExpression", ref?.regularExpression, assertion.regularExpression)
        }
    }

    private inline fun <reified T : SuuAssertionXmlContains> handleXmlContains(assertion: T) {
        handle(assertion) { ref ->
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
        }
    }

    override fun xpath(assertion: SuuAssertionXPath) {
        handleXmlContains(assertion)
    }

    override fun xquery(assertion: SuuAssertionXQuery) {
        handleXmlContains(assertion)
    }

    override fun exitAssertions(assertions: SuuAssertions) {


    }
}