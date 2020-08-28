package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.test.AbstractTest

fun main(args: Array<String>) {
    KarateTransformExp().transform()
}

class KarateTransformExp : AbstractTest() {

    private val env = Environment()

    fun transform() {

        val project = loadFromBox("runnable2") as SuProject
        val testCase = project.testSuites[0].testCases[0]
        val result = KarateGenerator().transform(testCase)

        println(result.main)
    }


}