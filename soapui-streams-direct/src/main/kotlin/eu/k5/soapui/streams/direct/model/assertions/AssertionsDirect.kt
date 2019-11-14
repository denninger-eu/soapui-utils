package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.GroovyScriptAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.ResponseSLAAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleContainsAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleNotContainsAssertion
import com.eviware.soapui.model.testsuite.TestAssertion
import com.eviware.soapui.security.assertion.InvalidHttpStatusCodesAssertion
import com.eviware.soapui.security.assertion.ValidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertions

class AssertionsDirect(
    private val assertionList: MutableList<TestAssertion>
) : SuuAssertions {

    override val assertions: List<SuuAssertion>
        get() = assertionList.map { mapAssertion(it) }

    companion object {

        fun mapAssertion(testAssertion: TestAssertion): SuuAssertion {
            if (testAssertion is InvalidHttpStatusCodesAssertion) {
                return AssertionInvalidStatusDirect(testAssertion)
            } else if (testAssertion is ValidHttpStatusCodesAssertion) {
                return AssertionValidStatusDirect(testAssertion)
            } else if (testAssertion is GroovyScriptAssertion) {
                return AssertionScriptDirect(testAssertion)
            } else if (testAssertion is ResponseSLAAssertion) {
                return AssertionDurationDirect(testAssertion)
            } else if (testAssertion is SimpleContainsAssertion) {
                return AssertionContainsDirect(testAssertion)
            } else if (testAssertion is SimpleNotContainsAssertion) {
                return AssertionNotContainsDirect(testAssertion)
            }
            TODO(testAssertion.javaClass.toString())
        }
    }
}