package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.test.*
import java.lang.IllegalStateException

class CopyTestStepListener(
    private val target: SuuTestCase
) : SuuTestStepListener {

    private val misc = SyncMisc()
    private var targetRestStep: SuuTestStepRestRequest? = null
    private var targetWsdlStep: SuuTestStepWsdlRequest? = null

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {

        val targetStep = target.createRestRequestStep(
            step.name,
            step.baseService,
            step.baseResources,
            step.baseMethod
        )
        handleStep(step, targetStep, false)
        targetStep.request.name = step.request.name
        targetStep.request.description = step.request.description
        targetStep.request.content = step.request.content
        for (header in step.request.headers) {
            targetStep.request.addOrUpdateHeader(header)
        }
        this.targetRestStep = targetStep
        return VisitResult.CONTINUE
    }

    override fun enterWsdlRequest(step: SuuTestStepWsdlRequest): VisitResult {
        val targetStep = target.createWsdlRequestStep(step.name, step.operationName)

        this.targetWsdlStep = targetStep
        return VisitResult.CONTINUE
    }


    override fun script(step: SuuTestStepScript) {
        val targetStep = target.createScriptStep(step.name)
        handleStep(step, targetStep)
        targetStep.script = step.script
    }


    override fun exitRestRequest(step: SuuTestStepRestRequest) {
        targetRestStep = null
    }

    override fun exitWsdlRequest(step: SuuTestStepWsdlRequest) {
        targetWsdlStep = null
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return when {
            targetRestStep != null -> CopyAssertionListener(targetRestStep!!.assertions)
            targetWsdlStep != null -> CopyAssertionListener(targetWsdlStep!!.assertions)
            else -> throw IllegalStateException("Missing testStep")
        }
    }

    private fun handleStep(reference: SuuTestStep, target: SuuTestStep, properties: Boolean = true) {
        target.description = reference.description
        target.enabled = reference.enabled
        target.weight = reference.weight

    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {
        val targetStep = target.createStep(step.name, SuuTestStepPropertyTransfers::class.java)
        handleStep(step, targetStep)

        for (refTransfer in step.transfers) {
            var target = targetStep.getTransfer(refTransfer.name)
            if (target == null) {
                target = targetStep.addTransfer(refTransfer.name)
            }
            misc.assignTransferProperties(target, refTransfer)
        }
    }

    override fun delay(step: SuuTestStepDelay) {
        val targetStep = target.createStep(step.name, SuuTestStepDelay::class.java)
        handleStep(step, targetStep)
        targetStep.delay = step.delay
    }


    override fun properties(step: SuuTestStepProperties) {
        val targetStep = target.createStep(step.name, SuuTestStepProperties::class.java)
        handleStep(step, targetStep)

        SyncListener.handleProperties(step.properties, targetStep.properties)
    }

}