package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.visitor.listener.*

class DifferenceTestStepListener(
    private val testCase: SuuTestCase,
    private val differences: Differences
) : SuuTestStepListener {

    private var restRequest: SuuTestStepRestRequest? = null

    private fun handleTestStep(ref: SuuTestStep, step: SuuTestStep, properties: Boolean = true) {
        differences.push(Differences.Type.TEST_STEP, step.name)
        differences.addChange("enabled", ref.enabled, step.enabled)
        differences.addChange("description", ref.description, step.description)

        if (properties) {
            DifferenceListener.handleProperties(differences, ref.properties, step.properties)
        }
    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {
        val ref = testCase.getStep(step.name)
        if (ref !is SuuTestStepPropertyTransfers) {
            differences.addChange(Differences.Type.TEST_STEP, step.name)
            return
        }
        handleTestStep(ref, step)
        differences.pop()
    }

    override fun delay(step: SuuTestStepDelay) {
        val ref = testCase.getStep(step.name)
        if (ref !is SuuTestStepDelay) {
            differences.addChange(Differences.Type.TEST_STEP, step.name)
            return
        }

        handleTestStep(ref, step)

        differences.addChange("delay", ref.delay, step.delay)
        differences.pop()
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        val ref = testCase.getStep(step.name)
        if (ref !is SuuTestStepRestRequest) {
            differences.addChange(Differences.Type.TEST_STEP, step.name)
            return VisitResult.TERMINATE
        }
        handleTestStep(ref, step, false)

        differences.addChange("content", ref.request.content?.trim(), step.request.content?.trim())

        restRequest = ref
        return VisitResult.CONTINUE
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
    }


    override fun createAssertionListener(): SuuAssertionListener {
        return DifferenceAssertionListener(differences, restRequest!!.assertions)
    }


}