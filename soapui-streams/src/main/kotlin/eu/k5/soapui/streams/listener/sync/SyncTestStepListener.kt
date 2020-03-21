package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.test.*

class SyncTestStepListener(
    private val env: Environment,
    private val reference: SuuTestCase,
    private val target: SuuTestCase
) : SuuTestStepListener {
    override fun enterWsdlRequest(step: SuuTestStepWsdlRequest): VisitResult {
        return VisitResult.CONTINUE
    }

    override fun exitWsdlRequest(step: SuuTestStepWsdlRequest) {
    }

    private val misc = SyncMisc()

    private var referenceRestRequest: SuuTestStepRestRequest? = null
    private var targetRestRequest: SuuTestStepRestRequest? = null

    private inline fun <reified T : SuuTestStep> handle(step: T, syncWith: (T) -> Unit) {
        val ref = reference.getStep(step.name)
        if (ref !is T) {
            return
        }
        step.description = ref.description
        step.enabled = ref.enabled
        step.weight = ref.weight
        syncWith(ref)
    }


    override fun properties(step: SuuTestStepProperties) {
        handle(step) {
            SyncListener.handleProperties(it.properties, step.properties)
        }
    }

    override fun script(step: SuuTestStepScript) {
        handle(step) {
            step.script = it.script
        }
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return SyncAssertionListener(referenceRestRequest!!, targetRestRequest!!)
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        val ref = reference.getStep(step.name)
        if (ref !is SuuTestStepRestRequest) {
            return VisitResult.TERMINATE
        }

        step.description = ref.description

        SyncRestRequest().handle(step.request, ref.request)


        targetRestRequest = step
        referenceRestRequest = ref
        return VisitResult.CONTINUE
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
        targetRestRequest = null
        referenceRestRequest = null
    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {
        handle(step) { ref ->
            val found = ArrayList<String>()
            for (transfer in step.transfers) {
                val refTransfer = ref.getTransfer(transfer.name)
                if (refTransfer == null) {

                    TODO("delete")
                }
                misc.assignTransferProperties(transfer, refTransfer)
                found.add(transfer.name)
            }
            val missing = ArrayList(ref.transfers)
            missing.removeIf { found.contains(it.name) }
            for (newTransfer in missing) {
                val target = step.addTransfer(newTransfer.name)
                misc.assignTransferProperties(target, newTransfer)
            }
        }

    }


    override fun delay(step: SuuTestStepDelay) {
        handle(step) {
            step.delay = it.delay
        }
    }


}