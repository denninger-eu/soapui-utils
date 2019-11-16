package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathContentAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathMatch

class AssertionJsonPathMatchDirect(
    private val jsonPath: JsonPathContentAssertion
) : AbstractAssertionJsonPathDirect(jsonPath), SuuAssertionJsonPathMatch {


}