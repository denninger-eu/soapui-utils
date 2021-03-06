package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.transform.ModelWriter
import java.io.StringWriter

fun main(args: Array<String>) {
    ManualGen().gen()
}

class ManualGen : AbstractTest() {

    fun gen() {
        val project = loadFromBox("runnable2") as SuProject
        val testCase = project.testSuites[0].testCases[0]

        val transformer = DispatchTransformer(testCase)

        val scenario = transformer.transform()


        val writer1 = ModelWriter().write(scenario)



        println(writer1.mainContent())
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