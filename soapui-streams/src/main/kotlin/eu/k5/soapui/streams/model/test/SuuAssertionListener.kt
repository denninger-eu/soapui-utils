package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.assertion.*

interface SuuAssertionListener {

    fun validStatus(assertion: SuuAssertionValidStatus)
    fun invalidStatus(assertion: SuuAssertionInvalidStatus)
    fun contains(assertion: SuuAssertionContains)
    fun notContains(assertion: SuuAssertionNotContains)
    fun script(assertion: SuuAssertionScript)
    fun duration(assertion: SuuAssertionDuration)

    fun exitAssertions(assertions: SuuAssertions)
}
