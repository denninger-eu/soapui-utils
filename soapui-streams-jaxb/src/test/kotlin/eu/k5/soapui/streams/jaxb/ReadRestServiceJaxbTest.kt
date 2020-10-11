package eu.k5.soapui.streams.jaxb

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ReadRestServiceJaxbTest : AbstractJaxbTest() {


    @Test
    fun readRestService() {
        val project = testProject("RestServiceComplete")

        Assertions.assertEquals(1, project.restServices.size)

        val restService = project.restServices[0]

        Assertions.assertEquals("RestServiceName", restService.name)
        Assertions.assertEquals("basePathValue", restService.basePath)
        Assertions.assertEquals("DescriptionValue", restService.description)

        Assertions.assertEquals(1, restService.resources.size)

        assertMainResource(restService.resources[0])
    }

    private fun assertMainResource(restResource: SuuRestResource) {
        Assertions.assertEquals("resourceName", restResource.name)
        Assertions.assertEquals("resourcePath", restResource.path)


        Assertions.assertEquals(1, restResource.methods.size)

        val method = restResource.methods[0]
        Assertions.assertEquals("methodName", method.name)
        Assertions.assertEquals(SuuRestMethod.HttpMethod.GET, method.httpMethod)

        Assertions.assertEquals(1, method.requests.size)

        val restRequest = method.requests[0]

        Assertions.assertEquals("requestName", restRequest.name)
        Assertions.assertEquals("requestDescription", restRequest.description)
        Assertions.assertEquals("application/json", restRequest.mediaType)
    }
}