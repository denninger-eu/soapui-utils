package eu.k5.soapui.transform.restassured

import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.test.ClasspathContentAssert
import org.junit.jupiter.api.Test

class RestAssuredGeneratorTest : AbstractTest() {


    @Test
    fun `properties`() {

        val testCase = loadTestcase("properties").testSuites[0].testCases[0]

        val transformer = RestAssuredGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/properties.java", result.getMainDocument())
    }

    @Test
    fun `propertyTransfers`() {
        val testCase = loadTestcase("propertytransfers").testSuites[0].testCases[0]

        val transformer = RestAssuredGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/propertytransfers.java", result.getMainDocument())
    }

    @Test
    fun `get restRequest`() {
        val testCase = loadTestcase("getrestrequest").testSuites[0].testCases[0]

        val transformer = RestAssuredGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/getrestrequest.java", result.getMainDocument())
    }

    @Test
    fun `post restRequest`() {
        val testCase = loadTestcase("postrestrequest").testSuites[0].testCases[0]

        val transformer = RestAssuredGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/postrestrequest.java", result.getMainDocument())
    }

    @Test
    fun `script`() {
        val testCase = loadTestcase("script").testSuites[0].testCases[0]

        val transformer = RestAssuredGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/script.java", result.getMainDocument())
    }
}