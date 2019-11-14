package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.ResponseSLAAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleContainsAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleNotContainsAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionContains
import eu.k5.soapui.streams.model.assertion.SuuAssertionNotContains

class AssertionNotContainsDirect(
    private val containsAssertion: SimpleNotContainsAssertion
) : AbstractAssertion(containsAssertion), SuuAssertionNotContains {


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
            if (containsAssertion.isUseRegEx) {
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