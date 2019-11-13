package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.direct.model.test.TestStepDelayDirect
import eu.k5.soapui.streams.listener.difference.DifferenceListener
import eu.k5.soapui.streams.listener.resource.SyncListener
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestSuiteDirectReadTest : AbstractDirectTest() {

    @Test
    fun writeBoxFromTestSuite() {
        val project = ProjectDirectTest.testProject("TestSuiteProject")

        val box = createTempProjectBox("TestSuiteProject")

        val apply = box.apply(SyncListener(project))

        val listener = DifferenceListener(project)
        box.apply(listener)

        assertTrue(listener.differences.isEmpty(), listener.differences.toString())
    }

    @Test
    fun readTestSuiteProject() {
        val project = ProjectDirectTest.testProject("TestSuiteProject")

        assertEquals(1, project.properties.properties.size)

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
        assertTrue(delayStep is TestStepDelayDirect)
        assertTrue(delayStep.enabled)
        assertEquals("DelayStepName", delayStep.name)
        assertEquals("DelayStepDescription", delayStep.description)
        assertEquals(1000, delayStep.delay)

        val delayStepDisabled = testCase.getStep("DelayStepDisabled")
        assertFalse(delayStepDisabled!!.enabled)

        assertTestStepPropertyTransfer(testCase.getStep("PropertyTransferName") as SuuTestStepPropertyTransfers)
    }


    private fun assertTestStepPropertyTransfer(propertyTransfer: SuuTestStepPropertyTransfers) {
        assertEquals("PropertyTransferName", propertyTransfer.name)
        assertEquals("PropertyTransferDescription", propertyTransfer.description)
        assertTrue(propertyTransfer.enabled)

        assertEquals(2, propertyTransfer.transfers.size)
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