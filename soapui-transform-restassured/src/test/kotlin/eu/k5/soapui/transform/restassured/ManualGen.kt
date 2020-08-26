package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.model.*
import java.io.StringWriter

fun main(args: Array<String>) {
    ManualGen().gen()
}

class ManualGen : AbstractTest() {

    fun gen() {
        val project = loadFromBox("runnable2") as SuProject
        val testCase = project.testSuites[0].testCases[0]

        val transformer = RaTransformer(testCase)

        val scenario = transformer.transform()


        val writer1 = StringWriter()
        val writer = ScenarioWriter(scenario, ModelWriter())


        writer.write()

        println(writer1.toString())
/*
        val writer = StringWriter()

        val step = RaRequestStep("create", "POST")

        step.addHeader("Authorization", "Bearer abc")
        step.addParameter("query", "value")


*/
/*
        val modelWriter = ModelWriter(writer, Environment())
        scenario().write(modelWriter)
*//*


        println(writer.toString())
*/
    }


}