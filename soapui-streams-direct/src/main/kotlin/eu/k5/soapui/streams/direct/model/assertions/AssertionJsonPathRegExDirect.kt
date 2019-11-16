package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathRegExAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathRegEx

class AssertionJsonPathRegExDirect(
    private val jsonPath: JsonPathRegExAssertion
) : AbstractAssertionJsonPathDirect(jsonPath), SuuAssertionJsonPathRegEx {

    override var regularExpression: String?
        get() = jsonPath.regularExpression
        set(value) {
            if (jsonPath.regularExpression != value){
                jsonPath.regularExpression = value
            }
        }


}