package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.assertion.SuuAssertions
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest

class TestStepRestRequestJaxb(
    testStep: TestStepElement
) : TestStepJaxb(testStep), SuuTestStepRestRequest {

    override val baseService: SuuRestService
        get() = TODO("Not yet implemented")
    override val baseResources: List<SuuRestResource>
        get() = TODO("Not yet implemented")
    override val baseMethod: SuuRestMethod
        get() = TODO("Not yet implemented")
    override var requestPath: SuuTestStepRestRequest.RequestPath
        get() = TODO("Not yet implemented")
        set(value) {}
    override val request: SuuRestRequest
        get() = TODO("Not yet implemented")
    override val assertions: SuuAssertions
        get() = TODO("Not yet implemented")

}