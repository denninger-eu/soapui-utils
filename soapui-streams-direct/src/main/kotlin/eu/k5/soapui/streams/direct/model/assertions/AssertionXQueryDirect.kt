package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.XQueryContainsAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionXQuery

class AssertionXQueryDirect(
    private val xquery: XQueryContainsAssertion
) : AbstractAssertionXmlDirect(xquery), SuuAssertionXQuery {


}