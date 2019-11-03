package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProjectBoxTest {

    @Test
    fun `load ProjectBox`() {

        val project = loadProject("projectonly")
        assertEquals("projectName", project.name)
        assertEquals("projectDescription", project.description)
        assertTrue(project.restServices.isEmpty())
    }

    @Test
    fun `load ProjectBox with RestService`() {
        val project = loadProject("restserviceonly")
        assertFalse(project.restServices.isEmpty())
        val restService = project.restServices[0]
        assertEquals("restserviceName", restService.name)
        assertEquals("restserviceDescription", restService.description)
        assertEquals("restserviceBasePath", restService.basePath)

        assertFalse(restService.resources.isEmpty())
        val resource = restService.resources[0]
        assertEquals("resourceName", resource.name)
        assertEquals("resourceDescription", resource.description)
        assertEquals("resourcePath", resource.path)

        assertFalse(resource.methods.isEmpty())
        val method = resource.methods[0]

        assertEquals("methodName", method.name)
        assertEquals("methodDescription", method.description)
        assertEquals(SuuRestMethod.HttpMethod.GET, method.method)

        assertFalse(method.requests.isEmpty())
        val request = method.requests[0]
        assertEquals("requestName", request.name)
        assertEquals("requestDescription", request.description)
        assertEquals("{ \"test\":\"test\" }\n", request.content)

    }

    companion object {
        fun loadProject(name: String): ProjectBox {
            val path = Paths.get("src", "test", "resources", "box", name, "project.box.yaml")
            return ProjectBox(Box(path))
        }
    }
}