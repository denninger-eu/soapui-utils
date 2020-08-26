package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists
import eu.k5.soapui.streams.model.assertion.SuuAssertionValidStatus
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.*
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star
import java.io.StringWriter
import java.io.Writer
import javax.swing.plaf.nimbus.State

fun main(args: Array<String>) {
    KarateTransformExp().transform()
}

class KarateTransformExp : AbstractTest() {

    private val env = Environment()

    fun transform() {

        val project = loadFromBox("runnable2") as SuProject
        val testCase = project.testSuites[0].testCases[0]
        val result = KarateTransformer().transform(testCase)

        println(result.main)
    }


}