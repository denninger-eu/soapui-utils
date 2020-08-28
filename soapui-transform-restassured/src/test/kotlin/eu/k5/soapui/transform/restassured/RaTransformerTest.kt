package eu.k5.soapui.transform.restassured

import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.test.ClasspathContentAssert
import org.junit.jupiter.api.Test

class RaTransformerTest : AbstractTest() {


    @Test
    fun `properties`() {

        val testCase = loadTestcase("properties").testSuites[0].testCases[0]

        val transformer = RestAssuredTransformer()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/properties/Testcase.java", result.main)
    }

    @Test
    fun `propertyTransfer`(){
        val testCase = loadTestcase("propertytransfers").testSuites[0].testCases[0]

        val transformer = RestAssuredTransformer()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("restassured/properties/Testcase.java", result.main)

    }

}