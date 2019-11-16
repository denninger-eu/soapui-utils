package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.ResponseSLAAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleContainsAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionContains

class AssertionContainsDirect(
    private val containsAssertion: SimpleContainsAssertion
) : AbstractAssertion(containsAssertion), SuuAssertionContains {


    override var content: String?
        get() = containsAssertion.token
        set(value) {
            if (containsAssertion.token != value) {
                containsAssertion.token = value
            }
        }
    override var regexp: Boolean
        get() = containsAssertion.isUseRegEx
        set(value) {
            if (containsAssertion.isUseRegEx != value) {
                containsAssertion.isUseRegEx = value
            }
        }
    override var caseSensitive: Boolean
        get() = !containsAssertion.isIgnoreCase
        set(value) {
            if (containsAssertion.isIgnoreCase == value) {
                containsAssertion.isIgnoreCase = !value
            }
        }

}