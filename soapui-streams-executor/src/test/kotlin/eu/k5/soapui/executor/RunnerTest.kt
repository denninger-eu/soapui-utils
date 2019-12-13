package eu.k5.soapui.executor

import eu.k5.soapui.streams.model.SuProject
import org.junit.jupiter.api.Test

class RunnerTest : AbstractTest() {

    @Test
    fun test() {
        val project = loadFromBox("Runnable") as SuProject
        val testCase = project.testSuites[0].testCases[0]

        val caseRunner = RunnerFactory().createRunner(testCase)

        val context = RunnerContext()
        context.addProperty("endpoint", "http://localhost:8080")
        caseRunner.initContext(context)
        caseRunner.run(context)
    }
}