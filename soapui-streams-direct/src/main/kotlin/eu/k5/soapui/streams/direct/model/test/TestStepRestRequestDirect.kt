package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import eu.k5.soapui.streams.direct.model.assertions.AssertionsDirect
import eu.k5.soapui.streams.direct.model.rest.RestRequestDirect
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import java.util.*

class TestStepRestRequestDirect(
    private val restRequestStep: RestTestRequestStep
) : AbstractTestStepDirect(
    restRequestStep,
    Arrays.asList("Endpoint", "Username", "Password", "Domain", "Request", "ResponseAsXml", "Response", "RawRequest")
), SuuTestStepRestRequest {


    init {
        val httpRequest = restRequestStep.httpRequest
    }

    override var request: SuuRestRequest
        get() = RestRequestDirect(restRequestStep.httpRequest as RestRequest)
        set(value) {}


    override val assertions: SuuAssertions
        get() = AssertionsDirect(restRequestStep.assertionList) { restRequestStep.addAssertion(it) }

}