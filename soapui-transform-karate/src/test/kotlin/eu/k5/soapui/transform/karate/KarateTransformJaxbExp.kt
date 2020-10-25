package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.jaxb.JaxbLoader
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.test.AbstractTest
import java.nio.file.Files

fun main(args: Array<String>) {
    KarateTransformJaxbExp().transform()
}

class KarateTransformJaxbExp : AbstractTest() {

    private val env = Environment()

    fun transform() {


        val root = searchRoot()

        val runnable = root.resolve("soapui-streams-jaxb").resolve("src/test/resources/testcases")
            .resolve("RestResources-soapui-project.xml")


        val project = Files.newInputStream(runnable).use { JaxbLoader().load(it) }
        val testCase = project.testSuites[0].testCases[0]
        val result = KarateGenerator().transform(testCase)

        println(result.getMainDocument())
    }


}