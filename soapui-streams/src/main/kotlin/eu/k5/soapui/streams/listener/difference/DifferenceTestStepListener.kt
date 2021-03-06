package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.test.*

class DifferenceTestStepListener(
    private val testCase: SuuTestCase,
    private val differences: Differences
) : SuuTestStepListener {



    private var restRequest: SuuTestStepRestRequest? = null

    private inline fun <reified T : SuuTestStep> handle(
        step: T,
        properties: Boolean = true,
        compareWith: (T) -> Unit
    ) {

        val ref = testCase.getStep(step.name)
        if (ref !is T) {
            differences.addChange(Differences.EntityType.TEST_STEP, step.name)
            return
        }
        differences.push(Differences.EntityType.TEST_STEP, step.name)
        differences.addChange("enabled", ref.enabled, step.enabled)
        differences.addChange("description", ref.description, step.description)
        compareWith(ref)
        differences.pop()
    }

    override fun properties(step: SuuTestStepProperties) {
        handle(step) { ref ->
            DifferenceListener.handleProperties(differences, ref.properties, step.properties)
        }
    }


    override fun transfer(step: SuuTestStepPropertyTransfers) {
        handle(step) { ref ->
            for (transfer in step.transfers) {
                if (!ref.hasTransfer(transfer.name)) {
                    differences.addAdditional(transfer.name)
                }
            }
            for (refTransfer in ref.transfers) {
                val transfer = step.getTransfer(refTransfer.name)
                if (transfer != null) {
                    transferDifference(refTransfer, transfer)
                } else {
                    differences.addMissing(refTransfer.name)
                }
            }

        }
    }

    private fun transferDifference(ref: SuuPropertyTransfer, target: SuuPropertyTransfer) {
        differences.addChange(target.name + ".s.expression", ref.source.expression, target.source.expression)
        differences.addChange(target.name + ".s.stepName", ref.source.stepName, target.source.stepName)
        differences.addChange(target.name + ".s.language", ref.source.language, target.source.language)
        differences.addChange(target.name + ".s.propertyName", ref.source.propertyName, target.source.propertyName)

        differences.addChange(target.name + ".t.expression", ref.target.expression, target.target.expression)
        differences.addChange(target.name + ".t.stepName", ref.target.stepName, target.target.stepName)
        differences.addChange(target.name + ".t.language", ref.target.language, target.target.language)
        differences.addChange(target.name + ".t.propertyName", ref.target.propertyName, target.target.propertyName)

    }

    override fun delay(step: SuuTestStepDelay) {
        handle(step) {
            differences.addChange("delay", it.delay, step.delay)
        }
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        val ref = testCase.getStep(step.name)
        if (ref !is SuuTestStepRestRequest) {
            differences.addChange(Differences.EntityType.TEST_STEP, step.name)
            return VisitResult.TERMINATE
        }

        differences.push(Differences.EntityType.TEST_STEP, step.name)
        differences.addChange("enabled", ref.enabled, step.enabled)
        differences.addChange("description", ref.description, step.description)
        DifferenceRestRequest(differences).handle(ref.request, step.request)

        restRequest = ref
        return VisitResult.CONTINUE
    }

    override fun enterWsdlRequest(step: SuuTestStepWsdlRequest): VisitResult {
        return VisitResult.CONTINUE
    }

    override fun exitWsdlRequest(step: SuuTestStepWsdlRequest) {
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
        differences.pop()
    }

    override fun script(step: SuuTestStepScript) {
        handle(step) {
            differences.addChange("script", it.script?.trim(), step.script?.trim())
        }
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return DifferenceAssertionListener(differences, restRequest!!.assertions)
    }


}