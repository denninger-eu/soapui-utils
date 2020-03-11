package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepWsdlRequest
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class ReadTestSuiteWsdlDirectTest : AbstractDirectTest() {
    @Test
    fun readWsdlService() {
        val project = testProject("WsdlService")

        val testSuite = project.getTestSuite("suite")
        assertNotNull(testSuite)
        val testCase = testSuite.getTestCase("testcase")
        assertNotNull(testCase)
        assertTestCase(testCase)
    }

    private fun assertTestCase(testCase: SuuTestCase) {

        println(testCase.name)
        println(testCase.steps)

        val step = testCase.getStep("createCountry")
        assert(step is SuuTestStepWsdlRequest)


    }
}