package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathCountAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathCount

class AssertionJsonPathCountDirect(
    private val jsonPath: JsonPathCountAssertion
) : AbstractAssertionJsonPathDirect(jsonPath), SuuAssertionJsonPathCount {

}