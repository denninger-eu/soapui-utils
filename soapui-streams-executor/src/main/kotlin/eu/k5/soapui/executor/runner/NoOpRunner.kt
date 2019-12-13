package eu.k5.soapui.executor.runner

import eu.k5.soapui.executor.Runner
import eu.k5.soapui.executor.RunnerContext
import eu.k5.soapui.streams.model.test.SuuTestStep

class NoOpRunner(
    private val step: SuuTestStep
) : Runner {

    override fun initContext(context: RunnerContext) {
    }

    override fun run(context: RunnerContext) {
        context.reportStep(step.name)
    }

}