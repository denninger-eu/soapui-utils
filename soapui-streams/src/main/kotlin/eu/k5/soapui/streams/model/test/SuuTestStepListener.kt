package eu.k5.soapui.streams.model.test

import eu.k5.soapui.visitor.listener.*


interface SuuTestStepListener {

    fun transfer(step: SuuTestStepPropertyTransfers)


    fun request(env: Environment, step: SuWsdlTestRequestStep)

    fun restRequest(step: SuuTestStepRestRequest)

    fun delay(step: SuuTestStepDelay)

    fun gotoStep(env: Environment, step: SuWsdlGotoTestStep)

    fun unsupported(env: Environment, step: SuTestStep) {

    }

    fun createAssertionListener(env: Environment, step: SuTestStep): SuAssertionListener?

    companion object {
        val NO_OP = object : SuuTestStepListener {
            override fun restRequest(step: SuuTestStepRestRequest) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun transfer(step: SuuTestStepPropertyTransfers) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun delay(step: SuuTestStepDelay) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun request(env: Environment, step: SuWsdlTestRequestStep) {
            }


            override fun gotoStep(env: Environment, step: SuWsdlGotoTestStep) {
            }

            override fun createAssertionListener(env: Environment, step: SuTestStep): SuAssertionListener? {
                return SuAssertionListener.NO_OP
            }

        }
    }
}