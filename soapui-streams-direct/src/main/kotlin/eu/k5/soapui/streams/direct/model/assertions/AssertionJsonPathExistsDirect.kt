package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathExistenceAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists

class AssertionJsonPathExistsDirect(
    private val jsonPath: JsonPathExistenceAssertion
) : AbstractAssertionJsonPathDirect(jsonPath), SuuAssertionJsonPathExists {


}