package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.TransformationResult
import eu.k5.soapui.transform.restassured.model.Environment
import eu.k5.soapui.transform.restassured.model.ScenarioWriter

class RestAssuredTransformer {
    fun transform(testCase: SuuTestCase): TransformationResult {


        val scenario = RaTransformer(testCase).transform()

        val modelWriter = ModelWriter()
        val writer = ScenarioWriter(scenario, modelWriter)
        writer.write()


        val result = TransformationResult(modelWriter.mainContent())
        return result
    }
}