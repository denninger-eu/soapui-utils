package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.ConfigRestRequestStepElement
import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest

class TestStepRestRequestJaxb(
    element: TestStepElement,
    val project: ProjectJaxb
) : TestStepJaxb(element), SuuTestStepRestRequest {

    private val config: ConfigRestRequestStepElement = element.config as ConfigRestRequestStepElement


    override val baseService: SuuRestService
        get() = project.getRestService(config.service ?: "")!!
    override val baseResources: List<SuuRestResource>
        get() = TODO("Not yet implemented")
    override val baseMethod: SuuRestMethod
        get() = TODO("Not yet implemented")
    override var requestPath: SuuTestStepRestRequest.RequestPath
        get() = TODO("Not yet implemented")
        set(value) {}
    override val request: SuuRestRequest
        get() = RestRequestJaxb(config.restRequest!!)

    override val assertions = AssertionsJaxb(config.restRequest?.assertions.orEmpty())

}