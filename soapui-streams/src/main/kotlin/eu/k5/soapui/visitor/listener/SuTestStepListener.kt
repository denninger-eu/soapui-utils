package eu.k5.soapui.visitor.listener


interface SuTestStepListener {

    fun request(env: Environment, step: SuWsdlTestRequestStep)

    fun delay(env: Environment, step: SuWsdlDelayTestStep)

    fun transfer(env: Environment, step: SuPropertyTransfersTestStep)

    fun gotoStep(env: Environment, step: SuWsdlGotoTestStep)

    fun unsupported(env: Environment, step: SuTestStep) {

    }

    fun createAssertionListener(env: Environment, step: SuTestStep): SuAssertionListener?
}