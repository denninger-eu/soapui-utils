package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.test.SuuAssertionListener
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest

class SyncAssertionListener(
    private val reference: SuuTestStepRestRequest,
    private val target: SuuTestStepRestRequest
) : SuuAssertionListener {


    private inline fun <reified T : SuuAssertion> handle(assertion: T, sync: (T) -> Unit) {
        val ref = reference.assertions.getAssertion(assertion.name)
        if (ref !is T) {

        } else {
            assertion.enabled = ref.enabled
            sync(ref)
        }
    }

    override fun validStatus(assertion: SuuAssertionValidStatus) {
        handle(assertion) {
            assertion.statusCodes = it.statusCodes
        }
    }

    override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
        handle(assertion) {
            assertion.statusCodes = it.statusCodes
        }
    }

    override fun contains(assertion: SuuAssertionContains) {
        handle(assertion) {
            assertion.regexp = it.regexp
            assertion.content = it.content
            assertion.caseSensitive = it.caseSensitive
        }
    }

    override fun notContains(assertion: SuuAssertionNotContains) {
        handle(assertion) {
            assertion.regexp = it.regexp
            assertion.content = it.content
            assertion.caseSensitive = it.caseSensitive
        }
    }

    override fun script(assertion: SuuAssertionScript) {
        handle(assertion) {
            assertion.script = it.script
        }
    }

    override fun duration(assertion: SuuAssertionDuration) {
        handle(assertion) {
            assertion.time = it.time
        }
    }

    override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
        handle(assertion) {
            assertion.expectedContent = it.expectedContent
            assertion.expression = it.expression
        }
    }

    override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
        handle(assertion) {
            assertion.expectedContent = it.expectedContent
            assertion.expression = it.expression
        }
    }

    override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
        handle(assertion) {
            assertion.expectedContent = it.expectedContent
            assertion.expression = it.expression
        }
    }

    override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
        handle(assertion) {
            assertion.expectedContent = it.expectedContent
            assertion.expression = it.expression
            assertion.regularExpression = it.regularExpression
        }
    }

    override fun xpath(assertion: SuuAssertionXPath) {
        handle(assertion) {
            assertion.expectedContent = it.expectedContent
            assertion.expression = it.expression
        }
    }

    override fun xquery(assertion: SuuAssertionXQuery) {
        handle(assertion) {
            assertion.expectedContent = it.expectedContent
            assertion.expression = it.expression
        }
    }

    override fun exitAssertions(assertions: SuuAssertions) {
    }

}