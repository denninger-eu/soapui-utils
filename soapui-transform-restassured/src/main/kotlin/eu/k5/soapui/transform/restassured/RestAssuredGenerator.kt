package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.transform.Generator
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.TransformationResult

class RestAssuredGenerator : Generator {
    override val name: String = "restassured"

    override fun transform(testCase: SuuTestCase): TransformationResult {
        val scenario = DispatchTransformer(testCase).transform()
        val modelWriter = ModelWriter().write(scenario)
        val result = TransformationResult(modelWriter.mainContent())
        return result
    }
}