package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.visitor.listener.*


interface SuuTestStepListener {

    fun transfer(step: SuuTestStepPropertyTransfers)


    fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult
    fun exitRestRequest(step: SuuTestStepRestRequest)


    fun delay(step: SuuTestStepDelay)


    fun createAssertionListener(): SuuAssertionListener

    companion object {
        val NO_OP = object : SuuTestStepListener {
            override fun exitRestRequest(step: SuuTestStepRestRequest) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createAssertionListener(): SuuAssertionListener {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


            override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun transfer(step: SuuTestStepPropertyTransfers) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun delay(step: SuuTestStepDelay) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        }
    }
}