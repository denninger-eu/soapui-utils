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

    override fun exitAssertions(assertions: SuuAssertions) {


    }
}