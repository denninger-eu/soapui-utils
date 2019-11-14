package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.visitor.listener.*

class DifferenceTestStepListener(
    private val testCase: SuuTestCase,
    private val differences: Differences
) : SuuTestStepListener {


    private fun handleTestStep(ref: SuuTestStep, step: SuuTestStep) {
        differences.push(Differences.Type.TEST_STEP, step.name)
        differences.addChange("enabled", ref.enabled, step.enabled)
        differences.addChange("description", ref.description, step.description)

        DifferenceListener.handleProperties(differences, ref.properties, step.properties)
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

    override fun restRequest(step: SuuTestStepRestRequest) {
        val ref = testCase.getStep(step.name)
        if (ref !is SuuTestStepRestRequest) {
            differences.addChange(Differences.Type.TEST_STEP, step.name)
            return
        }
        handleTestStep(ref, step)

        

    }

    override fun request(env: Environment, step: SuWsdlTestRequestStep) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun gotoStep(env: Environment, step: SuWsdlGotoTestStep) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createAssertionListener(env: Environment, step: SuTestStep): SuAssertionListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}