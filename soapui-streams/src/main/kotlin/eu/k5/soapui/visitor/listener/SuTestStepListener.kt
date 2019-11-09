package eu.k5.soapui.visitor.listener


interface SuTestStepListener {

    fun request(env: Environment, step: SuWsdlTestRequestStep)

    fun delay(env: Environment, step: SuWsdlDelayTestStep)

    fun transfer(env: Environment, step: SuPropertyTransfersTestStep)

    fun gotoStep(env: Environment, step: SuWsdlGotoTestStep)

    fun unsupported(env: Environment, step: SuTestStep) {

    }

    fun createAssertionListener(env: Environment, step: SuTestStep): SuAssertionListener?

    companion object {
        val NO_OP = object : SuTestStepListener {
            override fun request(env: Environment, step: SuWsdlTestRequestStep) {
            }

            override fun delay(env: Environment, step: SuWsdlDelayTestStep) {
            }

            override fun transfer(env: Environment, step: SuPropertyTransfersTestStep) {
            }

            override fun gotoStep(env: Environment, step: SuWsdlGotoTestStep) {
            }

            override fun createAssertionListener(env: Environment, step: SuTestStep): SuAssertionListener? {
                return SuAssertionListener.NO_OP
            }

        }
    }
}