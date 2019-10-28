package eu.k5.soapui.visitor.listener


interface SuAssertionListener {

    fun xpathContains(env: Environment, assertion: SuXPathContainsAssertion)

    fun soapFault(env: Environment, assertion: SuSoapFaultAssertion)

    fun soapResponse(env: Environment, assertion: SuSoapResponseAssertion)

    fun unsupported(env: Environment, assertion: SuTestAssertion)

}