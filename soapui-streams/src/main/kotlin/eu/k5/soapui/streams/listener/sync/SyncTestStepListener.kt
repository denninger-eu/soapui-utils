package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.test.*

class SyncTestStepListener(
    private val env: Environment,
    private val reference: SuuTestCase,
    private val target: SuuTestCase
) : SuuTestStepListener {

    private var referenceRestRequest: SuuTestStepRestRequest? = null
    private var targetRestRequest: SuuTestStepRestRequest? = null

    private inline fun <reified T : SuuTestStep> handle(step: T, syncWith: (T) -> Unit) {
        val ref = reference.getStep(step.name)
        if (ref !is T) {
            return
        }
        step.description = ref.description
        step.enabled = ref.enabled
        SyncListener.handleProperties(ref.properties, step.properties)
        syncWith(ref)
    }


    override fun properties(step: SuuTestStepProperties) {
        handle(step) {}
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return SyncAssertionListener(referenceRestRequest!!, targetRestRequest!!)
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        val ref = reference.getStep(step.name)
        if (ref !is SuuTestStepRestRequest) {
            return VisitResult.TERMINATE
        }
        targetRestRequest = step
        referenceRestRequest = ref
        return VisitResult.CONTINUE
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
        targetRestRequest = null
        referenceRestRequest = null
    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {
        handle(step) {

        }

    }

    override fun delay(step: SuuTestStepDelay) {
        handle(step) {
            step.delay = it.delay
        }
    }


}