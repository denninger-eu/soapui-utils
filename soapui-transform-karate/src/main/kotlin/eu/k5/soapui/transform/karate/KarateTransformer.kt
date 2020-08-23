package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStep
import eu.k5.soapui.transform.karate.model.Block
import eu.k5.soapui.transform.karate.model.Statement
import java.io.StringWriter
import java.io.Writer

class KarateTransformer {

    fun transform(testCase: SuuTestCase): TransformationResult {

        val environment = Environment()

        val scenario = MainTransformer(environment).transform(testCase)

        environment.write(scenario)

        return environment.getResult()
    }

}