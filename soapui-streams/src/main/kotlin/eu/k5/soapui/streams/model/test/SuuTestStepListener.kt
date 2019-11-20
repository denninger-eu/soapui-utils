package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.listener.VisitResult


interface SuuTestStepListener {

    fun transfer(step: SuuTestStepPropertyTransfers)


    fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult
    fun exitRestRequest(step: SuuTestStepRestRequest)

    fun properties(step: SuuTestStepProperties)

    fun delay(step: SuuTestStepDelay)

  //  fun script(step: SuuTestStepScript)

    fun createAssertionListener(): SuuAssertionListener

    companion object {
        val NO_OP = object : SuuTestStepListener {
/*            override fun script(step: SuuTestStepScript) {
            }*/

            override fun properties(step: SuuTestStepProperties) {
            }

            override fun exitRestRequest(step: SuuTestStepRestRequest) {
            }

            override fun createAssertionListener(): SuuAssertionListener {
                return SuuAssertionListener.NO_OP
            }


            override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
                return VisitResult.TERMINATE
            }

            override fun transfer(step: SuuTestStepPropertyTransfers) {
            }

            override fun delay(step: SuuTestStepDelay) {
            }


        }
    }
}