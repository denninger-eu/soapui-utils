package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.RestResource
import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import eu.k5.soapui.streams.direct.model.assertions.AssertionsDirect
import eu.k5.soapui.streams.direct.model.rest.ResourceDirect
import eu.k5.soapui.streams.direct.model.rest.RestMethodDirect
import eu.k5.soapui.streams.direct.model.rest.RestRequestDirect
import eu.k5.soapui.streams.direct.model.rest.RestServiceDirect
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import kotlin.collections.ArrayList

class TestStepRestRequestDirect(
    testCase: TestCaseDirect,
    private val restRequestStep: RestTestRequestStep
) : AbstractTestStepDirect(
    testCase,
    restRequestStep
), SuuTestStepRestRequest {
    override var requestPath: SuuTestStepRestRequest.RequestPath
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}


    override val baseService: SuuRestService
        get() = RestServiceDirect(resolveRestService())
    override val baseResources: List<SuuRestResource>
        get() = resolveRestResources()
    override val baseMethod: SuuRestMethod
        get() = RestMethodDirect(restRequestStep.testRequest.restMethod)


    private fun resolveRestService(): RestService {
        val method = restRequestStep.testRequest.restMethod
        val resource = method.resource
        return resource.service
    }

    private fun resolveRestResources(): List<ResourceDirect> {
        val method = restRequestStep.testRequest.restMethod
        val resources = ArrayList<RestResource>()
        resources.add(method.resource)
        while (resources[0].parentResource != null) {
            resources.add(0, resources[0].parentResource)
        }

        val result = ArrayList<ResourceDirect>()
        var parent: ResourceDirect? = null

        for (resource in resources) {
            val newResource = ResourceDirect(resource, parent)
            result.add(newResource)
            parent = newResource
        }
        return result
    }


    override val request: SuuRestRequest
        get() = RestRequestDirect(restRequestStep.httpRequest as RestRequest)


    override val assertions: SuuAssertions
        get() = AssertionsDirect(restRequestStep.assertionList) { restRequestStep.addAssertion(it) }

}