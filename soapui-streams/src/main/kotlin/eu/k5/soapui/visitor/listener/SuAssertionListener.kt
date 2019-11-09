package eu.k5.soapui.visitor.listener


interface SuAssertionListener {

    fun xpathContains(env: Environment, assertion: SuXPathContainsAssertion)

    fun soapFault(env: Environment, assertion: SuSoapFaultAssertion)

    fun soapResponse(env: Environment, assertion: SuSoapResponseAssertion)

    fun unsupported(env: Environment, assertion: SuTestAssertion)

    companion object {
        val NO_OP = object : SuAssertionListener {
            override fun xpathContains(env: Environment, assertion: SuXPathContainsAssertion) {
            }

            override fun soapFault(env: Environment, assertion: SuSoapFaultAssertion) {
            }

            override fun soapResponse(env: Environment, assertion: SuSoapResponseAssertion) {
            }

            override fun unsupported(env: Environment, assertion: SuTestAssertion) {
            }
        }
    }
}