package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.wsdl.WsdlTestSuite
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.model.testsuite.TestSuite
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite

class TestSuiteDirect(
    private val testSuite: WsdlTestSuite
) : SuuTestSuite {


    override var name: String
        get() = testSuite.name ?: ""
        set(value) {
            testSuite.name = value
        }


    override var enabled: Boolean
        get() = !testSuite.isDisabled
        set(value) {
            testSuite.isDisabled = !value
        }

    override val testCases: List<TestCaseDirect>
        get() = testSuite.testCaseList.filterIsInstance<WsdlTestCase>().map { TestCaseDirect(it) }


}