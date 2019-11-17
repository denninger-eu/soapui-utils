package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import eu.k5.soapui.streams.direct.model.assertions.AssertionsDirect
import eu.k5.soapui.streams.direct.model.rest.RestMethodDirect
import eu.k5.soapui.streams.direct.model.rest.RestRequestDirect
import eu.k5.soapui.streams.direct.model.rest.RestServiceDirect
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import java.util.*

class TestStepRestRequestDirect(
    private val restRequestStep: RestTestRequestStep
) : AbstractTestStepDirect(
    restRequestStep,
    Arrays.asList("Endpoint", "Username", "Password", "Domain", "Request", "ResponseAsXml", "Response", "RawRequest")
), SuuTestStepRestRequest {


    override val baseService: SuuRestService
        get() = RestServiceDirect(resolveRestService())
    override val baseResource: List<SuuRestResource>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val baseMethod: SuuRestMethod
        get() = RestMethodDirect(restRequestStep.testRequest.restMethod)

    private fun resolveRestService(): RestService {
        val method = restRequestStep.testRequest.restMethod
        val resource = method.resource
        return resource.service
    }

    init {
        val httpRequest = restRequestStep.httpRequest
    }

    override var request: SuuRestRequest
        get() = RestRequestDirect(restRequestStep.httpRequest as RestRequest)
        set(value) {}


    override val assertions: SuuAssertions
        get() = AssertionsDirect(restRequestStep.assertionList) { restRequestStep.addAssertion(it) }

}