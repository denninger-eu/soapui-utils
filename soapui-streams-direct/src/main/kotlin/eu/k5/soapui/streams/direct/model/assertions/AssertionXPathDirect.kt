package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.XPathContainsAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionXPath

class AssertionXPathDirect(
    private val xpath: XPathContainsAssertion
) : AbstractAssertionXmlDirect(xpath), SuuAssertionXPath {


}