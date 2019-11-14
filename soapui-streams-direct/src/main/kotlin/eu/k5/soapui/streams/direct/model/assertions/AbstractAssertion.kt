package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion
import com.eviware.soapui.model.testsuite.TestAssertion
import com.eviware.soapui.security.assertion.InvalidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertion

open class AbstractAssertion(
    private val testAssertion: WsdlMessageAssertion
) : SuuAssertion {

    override var name: String
        get() = testAssertion.name
        set(value) {
            if (testAssertion.name != value) {
                testAssertion.name = value
            }
        }
    override var enabled: Boolean
        get() = !testAssertion.isDisabled
        set(value) {
            if (testAssertion.isDisabled == value) {
                testAssertion.isDisabled = !value
            }
        }

}