package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStep
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.karate.model.Block
import eu.k5.soapui.transform.karate.model.Statement

class KarateTransformer {


    fun transform(testCase: SuuTestCase): TransformationResult {
        val result = Exp().transform(testCase)

        return TransformationResult(result)
    }

    private fun transformHeader(testCase: SuuTestCase): Statement {
        val block = Block()
        for (step in testCase.steps) {

        }
        return block
    }

    private inline fun <reified T : SuuTestStep> resolveTransformer(step: T) {


    }
}