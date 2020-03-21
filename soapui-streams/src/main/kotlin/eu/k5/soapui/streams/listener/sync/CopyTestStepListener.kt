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

    override fun enterRestRequest(refStep: SuuTestStepRestRequest): VisitResult {

        val targetStep = target.createRestRequestStep(
            refStep.name,
            refStep.baseService,
            refStep.baseResources,
            refStep.baseMethod
        )
        handleStep(refStep, targetStep, false)
        targetStep.request.name = refStep.request.name
        targetStep.request.description = refStep.request.description
        targetStep.request.content = refStep.request.content
        for (header in refStep.request.headers) {
            targetStep.request.addOrUpdateHeader(header)
        }
        this.targetRestStep = targetStep
        return VisitResult.CONTINUE
    }

    override fun enterWsdlRequest(refStep: SuuTestStepWsdlRequest): VisitResult {
        val targetStep = target.createWsdlRequestStep(refStep.name, refStep.operationName)

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

    override fun transfer(refStep: SuuTestStepPropertyTransfers) {
        val targetStep = target.createStep(refStep.name, SuuTestStepPropertyTransfers::class.java)
        handleStep(refStep, targetStep)

        val missing = ArrayList<String>()
        for (refTransfer in refStep.transfers) {
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