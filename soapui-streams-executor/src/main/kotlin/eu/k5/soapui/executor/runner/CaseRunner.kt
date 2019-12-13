package eu.k5.soapui.executor.runner

import eu.k5.soapui.executor.Runner
import eu.k5.soapui.executor.RunnerContext
import eu.k5.soapui.streams.model.test.SuuTestCase

class CaseRunner(
    private val testCase: SuuTestCase,
    private val steps: List<Runner>
) : Runner {

    override fun initContext(context: RunnerContext) {
        steps.forEach { it.initContext(context) }
    }

    override fun run(context: RunnerContext) {
        steps.forEach { it.run(context) }
    }

}