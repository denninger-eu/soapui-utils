package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.sync.SyncListener
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepWsdlRequest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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


        val step = testCase.getStep("createCountry")
        assert(step is SuuTestStepWsdlRequest)

        if (step !is SuuTestStepWsdlRequest) {
            return
        }
        assertEquals("createCountry", step.operation.name)

        assertEquals(
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:coun=\"http://k5.eu/dread/countries\">\r\n" +
                    "   <soapenv:Header/>\r\n" +
                    "   <soapenv:Body>\r\n" +
                    "      <coun:createCountryRequest>\r\n" +
                    "         <coun:country>\r\n" +
                    "            <coun:name>Italy</coun:name>\r\n" +
                    "            <coun:population>100</coun:population>\r\n" +
                    "            <coun:capital>Rome</coun:capital>\r\n" +
                    "         </coun:country>\r\n" +
                    "      </coun:createCountryRequest>\r\n" +
                    "   </soapenv:Body>\r\n" +
                    "</soapenv:Envelope>", step.request.content
        )

    }

    @Test
    fun writeBox(){
        val project = testProject("WsdlService")
        val box = createTempProjectBox("WsdlService")
        box.apply(SyncListener(project))

    }
}