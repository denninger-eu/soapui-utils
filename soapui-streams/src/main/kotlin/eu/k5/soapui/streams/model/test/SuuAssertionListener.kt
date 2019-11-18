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

    companion object {
        val NO_OP = object : SuuAssertionListener {
            override fun validStatus(assertion: SuuAssertionValidStatus) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun contains(assertion: SuuAssertionContains) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun notContains(assertion: SuuAssertionNotContains) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun script(assertion: SuuAssertionScript) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun duration(assertion: SuuAssertionDuration) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun xpath(assertion: SuuAssertionXPath) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun xquery(assertion: SuuAssertionXQuery) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun exitAssertions(assertions: SuuAssertions) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }
}
