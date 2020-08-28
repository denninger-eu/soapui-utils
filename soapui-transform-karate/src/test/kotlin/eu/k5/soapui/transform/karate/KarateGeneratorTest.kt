package eu.k5.soapui.transform.karate

import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.test.ClasspathContentAssert
import org.junit.jupiter.api.Test

class KarateGeneratorTest {

    @Test
    fun `properties`() {

        val testCase = AbstractTest.loadTestcase("properties").testSuites[0].testCases[0]

        val transformer = KarateGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("karate/properties.feature", result.main)
    }

    @Test
    fun `propertyTransfer`() {

        val testCase = AbstractTest.loadTestcase("propertytransfers").testSuites[0].testCases[0]

        val transformer = KarateGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("karate/propertytransfers.feature", result.main)
    }

    @Test
    fun `restRequest`() {

        val testCase = AbstractTest.loadTestcase("propertytransfers").testSuites[0].testCases[0]

        val transformer = KarateGenerator()
        val result = transformer.transform(testCase)

        ClasspathContentAssert.equals("karate/propertytransfers.feature", result.main)
    }
}