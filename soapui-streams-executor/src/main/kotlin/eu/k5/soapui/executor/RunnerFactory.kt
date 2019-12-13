package eu.k5.soapui.executor

import eu.k5.soapui.executor.runner.*
import eu.k5.soapui.streams.model.test.*

class RunnerFactory {
    private val clientFactory = ClientFactory()

    fun createRunner(testCase: SuuTestCase): CaseRunner {
        val stepRunners = testCase.steps.map { createRunner(it) }.toList()
        return CaseRunner(testCase, stepRunners)
    }

    private fun createRunner(testStep: SuuTestStep): Runner {
        if (testStep is SuuTestStepProperties) {
            return PropertiesRunner(testStep)
        } else if (testStep is SuuTestStepPropertyTransfers) {
            return PropertyTransferRunner(testStep)
        } else if (testStep is SuuTestStepRestRequest) {
            return RestRequestRunner(testStep, clientFactory)
        } else {
            return NoOpRunner(testStep)
        }
    }
}