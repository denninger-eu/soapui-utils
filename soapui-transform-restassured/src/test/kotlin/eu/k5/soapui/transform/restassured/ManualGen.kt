package eu.k5.soapui.transform.restassured

import eu.k5.soapui.transform.restassured.model.RaRequestStep
import java.io.StringWriter

fun main(args: Array<String>) {

}

class ManualGen {

    fun gen() {
        val writer = StringWriter()

        val step = RaRequestStep("create", "POST")

        step.addHeader("Authorization", "Bearer abc")
        step.addParameter("query", "value")




/*
        val modelWriter = ModelWriter(writer, Environment())
        scenario().write(modelWriter)
*/

        println(writer.toString())
    }
}