package eu.k5.soapui.streams.jaxb

import eu.k5.soapui.streams.jaxb.model.TestStepDelayJaxb
import eu.k5.soapui.streams.model.test.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ReadWsdlServiceJaxbTest : AbstractJaxbTest() {


    @Test
    fun wsdl() {
        val project = testProject("WsdlServiceProject")

    }

    @Test
    fun readProject() {
        val project = testProject("TestSuiteProject")


        assertEquals(2, project.properties.properties.size, "project.properties")


        assertEquals("TestSuiteProject", project.name)

        val property = project.properties.properties[0]
        assertEquals("ProjectPropertyName", property.name)
        assertEquals("ProjectPropertyValue", property.value)


        assertEquals(2, project.testSuites.size)
        val testSuite = project.getTestSuite("TestSuiteName")

        assertFalse(project.getTestSuite("TestSuiteDisabled")!!.enabled)

        assertNotNull(testSuite)
        assertEquals("TestSuiteName", testSuite.name)
        assertTrue(testSuite.enabled)

        assertEquals(2, testSuite.testCases.size)

        assertFalse(testSuite.getTestCase("TestCaseDisabled")!!.enabled)

        assertTestCase(testSuite.getTestCase("TestCaseName")!!)
    }

    private fun assertTestCase(testCase: SuuTestCase) {
        assertEquals("TestCaseName", testCase.name)
        assertTrue(testCase.enabled)

        val step = testCase.steps

        val delayStep = testCase.getStep("DelayStepName")
        assertNotNull(delayStep)
        assertTrue(delayStep is TestStepDelayJaxb)
        assertTrue(delayStep.enabled)
        assertEquals("DelayStepName", delayStep.name)
        assertEquals("DelayStepDescription", delayStep.description)
        assertEquals(2000, delayStep.delay)

        val delayStepDisabled = testCase.getStep("DelayStepDisabled")
        assertFalse(delayStepDisabled!!.enabled)

        assertTestStepPropertyTransfer(testCase.getStep("PropertyTransferName") as SuuTestStepPropertyTransfers)
        /*     assertTestStepRestRequest(testCase.getStep("restRequestStep") as SuuTestStepRestRequest)
             assertTestStepProperties(testCase.getStep("PropertiesStepName") as SuuTestStepProperties)
     */
        val script = testCase.getStep("groovyScript") as SuuTestStepScript
        assertEquals("groovyScriptValue", script.script)
    }

    private fun assertTestStepPropertyTransfer(propertyTransfer: SuuTestStepPropertyTransfers) {
        assertEquals("PropertyTransferName", propertyTransfer.name)
        assertEquals("PropertyTransferDescription", propertyTransfer.description)
        assertTrue(propertyTransfer.enabled)

        assertEquals(3, propertyTransfer.transfers.size)
        val transfer = propertyTransfer.transfers[0]

        assertEquals("transferName", transfer.name)
        assertTrue(transfer.enabled)

        assertEquals(SuuPropertyTransfer.Language.XPATH, transfer.source.language)
        assertEquals(SuuPropertyTransfer.Language.JSONPATH, transfer.target.language)

        assertEquals("sourceTransferExpression", transfer.source.expression)
        assertEquals("targetTransferExpression", transfer.target.expression)
        assertEquals("ProjectPropertyName", transfer.source.propertyName)
        assertEquals("TestSuitePropertyName", transfer.target.propertyName)
        assertEquals("#Project#", transfer.source.stepName)
        assertEquals("#TestSuite#", transfer.target.stepName)

        val suuPropertyTransfer = propertyTransfer.transfers[1]
        assertEquals("transferNameDisabled", suuPropertyTransfer.name)
        assertFalse(suuPropertyTransfer.enabled)

    }
}