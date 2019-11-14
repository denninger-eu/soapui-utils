package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.ResponseSLAAssertion
import com.eviware.soapui.security.assertion.InvalidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionDuration
import eu.k5.soapui.streams.model.assertion.SuuAssertionInvalidStatus

class AssertionDurationDirect(
    private val responseSla: ResponseSLAAssertion
) : AbstractAssertion(responseSla), SuuAssertionDuration {
    override var time: String?
        get() = responseSla.sla
        set(value) {
            if (responseSla.sla != value){
                responseSla.sla = value
            }
        }

}
