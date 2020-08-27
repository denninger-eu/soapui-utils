package eu.k5.soapui.streams.jaxb

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

        //  assertEquals(2, testSuite.testCases.size)

        //assertFalse(testSuite.getTestCase("TestCaseDisabled")!!.enabled)

        //  assertTestCase(testSuite.getTestCase("TestCaseName")!!)
    }
}