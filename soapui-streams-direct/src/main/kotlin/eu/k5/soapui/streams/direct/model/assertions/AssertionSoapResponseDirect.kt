package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.soap.SoapResponseAssertion
import com.eviware.soapui.security.assertion.ValidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionSoapResponse

class AssertionSoapResponseDirect(
    private val soapResponse: SoapResponseAssertion
) : AbstractAssertion(soapResponse), SuuAssertionSoapResponse {
}