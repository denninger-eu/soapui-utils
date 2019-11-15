package eu.k5.soapui.streams.listener.copy

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

    override fun exitAssertions(assertions: SuuAssertions) {
    }
}

