package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.security.assertion.InvalidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionInvalidStatus

class AssertionInvalidStatusDirect(
    private val invalidStatusCodes: InvalidHttpStatusCodesAssertion
) : AbstractAssertion(invalidStatusCodes), SuuAssertionInvalidStatus {


    override var statusCodes: String
        get() = invalidStatusCodes.codes
        set(value) {
            if (invalidStatusCodes.codes != value) {
                invalidStatusCodes.codes = value
            }
        }
}