package eu.k5.soapui.streams.listener.copy

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.listener.resource.SyncListener
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.visitor.listener.*

class CopyTestStepListener(
    private val target: SuuTestCase
) : SuuTestStepListener {


    private var targetStep: SuuTestStepRestRequest? = null

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


        this.targetStep = targetStep


        return VisitResult.CONTINUE
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
        targetStep = null
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return CopyAssertionListener(targetStep!!.assertions)
    }

    private fun handleStep(reference: SuuTestStep, target: SuuTestStep, properties: Boolean = true) {
        target.description = reference.description
        target.enabled = reference.enabled

        if (properties) {
            SyncListener.handleProperties(reference.properties, target.properties)
        }

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

            target.enabled = refTransfer.enabled
            target.source.language = refTransfer.source.language
            target.source.expression = refTransfer.source.expression
            target.source.stepName = refTransfer.source.stepName
            target.source.propertyName = refTransfer.source.propertyName

            target.target.language = refTransfer.target.language
            target.target.expression = refTransfer.target.expression
            target.target.stepName = refTransfer.target.stepName
            target.target.propertyName = refTransfer.target.propertyName
        }
    }

    override fun delay(step: SuuTestStepDelay) {
        val targetStep = target.createStep(step.name, SuuTestStepDelay::class.java)
        handleStep(step, targetStep)
        targetStep.delay = step.delay
    }


}