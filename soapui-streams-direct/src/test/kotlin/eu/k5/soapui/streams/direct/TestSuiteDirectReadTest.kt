package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.direct.model.test.TestStepDelayDirect
import eu.k5.soapui.streams.flatten
import eu.k5.soapui.streams.listener.difference.DifferenceListener
import eu.k5.soapui.streams.listener.sync.SyncListener
import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.streams.select
import eu.k5.soapui.streams.syncWith
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

        val firstDifference = DifferenceListener(project)

        assertTrue(firstDifference.differences.isEmpty(), firstDifference.differences.toString())

        // double apply should not affect differences
        box.apply(SyncListener(project))

        val secondDifference = DifferenceListener(project)
        box.apply(secondDifference)

        assertTrue(secondDifference.differences.isEmpty(), secondDifference.differences.toString())
    }


    @Test
    fun readTestSuiteProject() {
        val project = ProjectDirectTest.testProject("TestSuiteProject")

        assertEquals(2, project.properties.properties.size)

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
        assertEquals(2000, delayStep.delay)

        val delayStepDisabled = testCase.getStep("DelayStepDisabled")
        assertFalse(delayStepDisabled!!.enabled)

        assertTestStepPropertyTransfer(testCase.getStep("PropertyTransferName") as SuuTestStepPropertyTransfers)
        assertTestStepRestRequest(testCase.getStep("restRequestStep") as SuuTestStepRestRequest)
        assertTestStepProperties(testCase.getStep("PropertiesStepName") as SuuTestStepProperties)
    }

    private fun assertTestStepProperties(step: SuuTestStepProperties) {
        assertEquals("PropertiesStepDescription", step.description)

        val property = step.properties.byName("propertyName")
        assertNotNull(property)
        assertEquals("propertyValue", property.value)
    }

    private fun assertTestStepRestRequest(restRequest: SuuTestStepRestRequest) {
        val request = restRequest.request


        val invalidCodes = restRequest.assertions.getAssertion("invalidCodes") as SuuAssertionInvalidStatus
        assertTrue(invalidCodes.enabled)
        assertEquals("400", invalidCodes.statusCodes)

        val validCodes = restRequest.assertions.getAssertion("validCodes") as SuuAssertionValidStatus
        assertTrue(validCodes.enabled)
        assertEquals("200", validCodes.statusCodes)

        val script = restRequest.assertions.getAssertion("script") as SuuAssertionScript
        assertTrue(script.enabled)
        assertEquals("scriptValue", script.script)

        val sla = restRequest.assertions.getAssertion("sla") as SuuAssertionDuration
        assertTrue(sla.enabled)
        assertEquals("200", sla.time)


        val contains = restRequest.assertions.getAssertion("contains") as SuuAssertionContains
        assertTrue(contains.enabled)
        assertEquals("contentValue", contains.content)
        assertFalse(contains.caseSensitive)
        assertTrue(contains.regexp)

        val notContains = restRequest.assertions.getAssertion("notContains") as SuuAssertionNotContains
        assertTrue(notContains.enabled)
        assertEquals("notContainsContent", notContains.content)
        assertTrue(notContains.caseSensitive)
        assertFalse(notContains.regexp)


        val jsonPathMatch = restRequest.assertions.getAssertion("jsonPath") as SuuAssertionJsonPathMatch
        assertTrue(jsonPathMatch.enabled)
        assertEquals("jsonPathExpressionValue", jsonPathMatch.expression)
        assertEquals("jsonPathExpectedResultValue", jsonPathMatch.expectedContent)

        val jsonPathCount = restRequest.assertions.getAssertion("jsonPathCount") as SuuAssertionJsonPathCount
        assertTrue(jsonPathCount.enabled)
        assertEquals("jsonPathCountExpressionValue", jsonPathCount.expression)
        assertEquals("jsonPathCountExpectedResultValue", jsonPathCount.expectedContent)

        val jsonPathRegEx = restRequest.assertions.getAssertion("jsonPathRegEx") as SuuAssertionJsonPathRegEx
        assertTrue(jsonPathRegEx.enabled)
        assertEquals("jsonPathRegExExpressionValue", jsonPathRegEx.expression)
        assertEquals("jsonPathRegExRegExpValue", jsonPathRegEx.regularExpression)
        assertEquals("jsonPathRegExExpectedResultValue", jsonPathRegEx.expectedContent)


        val xpath = restRequest.assertions.getAssertion("xpath") as SuuAssertionXPath
        assertTrue(xpath.enabled)
        assertEquals("xpathValue", xpath.expression)
        assertEquals("xpathExpectedResult", xpath.expectedContent)
        assertFalse(xpath.allowWildcards)
        assertFalse(xpath.ignoreComments)
        assertFalse(xpath.ignoreNamespaceDifferences)

        val xquery = restRequest.assertions.getAssertion("xquery") as SuuAssertionXQuery
        assertTrue(xquery.enabled)
        assertEquals("xqueryExpressionValue", xquery.expression)
        assertEquals("xqueryExpectedResult", xquery.expectedContent)
        assertTrue(xquery.allowWildcards)
        assertTrue(xquery.ignoreComments)
        assertTrue(xquery.ignoreNamespaceDifferences)


/*        val jsonPathExists = restRequest.assertions.getAssertion("jsonPathExists") as SuuAssertionJsonPathExists
        assertTrue(jsonPathExists.enabled)
        assertEquals("jsonPathExistsExpressionValue", jsonPathExists.expression)
        assertEquals("jsonPathExistsExpectedResultValue", jsonPathExists.expectedContent)*/

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