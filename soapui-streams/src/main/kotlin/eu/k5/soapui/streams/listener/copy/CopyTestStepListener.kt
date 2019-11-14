package eu.k5.soapui.streams.listener.copy

import eu.k5.soapui.streams.listener.resource.SyncListener
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.visitor.listener.*

class CopyTestStepListener(
    private val target: SuuTestCase
) : SuuTestStepListener {


    override fun restRequest(refStep: SuuTestStepRestRequest) {
        val targetStep = target.createStep(refStep.name, SuuTestStepRestRequest::class.java)
        handleStep(refStep, targetStep)



    }

    private fun handleStep(reference: SuuTestStep, target: SuuTestStep) {
        target.description = reference.description
        target.enabled = reference.enabled

        SyncListener.handleProperties(reference.properties, target.properties)

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
            target.source.propertyName = refTransfer.source.propertyName
            target.source.stepName = refTransfer.source.stepName

            target.target.language = refTransfer.target.language
            target.target.expression = refTransfer.target.expression
            target.target.propertyName = refTransfer.target.propertyName
            target.target.stepName = refTransfer.target.stepName
        }
    }

    override fun delay(step: SuuTestStepDelay) {
        val targetStep = target.createStep(step.name, SuuTestStepDelay::class.java)
        handleStep(step, targetStep)
        targetStep.delay = step.delay
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