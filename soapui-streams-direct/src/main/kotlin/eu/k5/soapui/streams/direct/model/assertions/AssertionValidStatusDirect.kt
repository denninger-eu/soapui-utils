package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.security.assertion.ValidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionValidStatus

class AssertionValidStatusDirect(
    private val validStatusCodes: ValidHttpStatusCodesAssertion
) : AbstractAssertion(validStatusCodes), SuuAssertionValidStatus {
    override var statusCodes: String
        get() = validStatusCodes.codes
        set(value) {
            if (validStatusCodes.codes != value) {
                validStatusCodes.codes = value
            }
        }
}
