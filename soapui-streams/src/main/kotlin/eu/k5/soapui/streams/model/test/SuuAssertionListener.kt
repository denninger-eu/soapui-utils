package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.assertion.*

interface SuuAssertionListener {

    fun validStatus(assertion: SuuAssertionValidStatus)
    fun invalidStatus(assertion: SuuAssertionInvalidStatus)
    fun contains(assertion: SuuAssertionContains)
    fun notContains(assertion: SuuAssertionNotContains)
    fun script(assertion: SuuAssertionScript)
    fun duration(assertion: SuuAssertionDuration)

    fun jsonPathCount(assertion: SuuAssertionJsonPathCount)
    fun jsonPathExits(assertion: SuuAssertionJsonPathExists)
    fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch)
    fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx)

    fun xpath(assertion: SuuAssertionXPath)
    fun xquery(assertion: SuuAssertionXQuery)

    fun exitAssertions(assertions: SuuAssertions)
}
