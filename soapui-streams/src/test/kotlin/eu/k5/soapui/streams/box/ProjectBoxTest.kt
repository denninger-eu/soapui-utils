package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.resource.SyncListener
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
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

        assertEquals(1, restService.resources.size)
        val resource = restService.resources[0]
        assertEquals("resourceName", resource.name)
        assertEquals("resourceDescription", resource.description)
        assertEquals("resourcePath", resource.path)

        assertEquals(1, resource.methods.size)
        val method = resource.methods[0]

        assertEquals("methodName", method.name)
        assertEquals("methodDescription", method.description)
        assertEquals(SuuRestMethod.HttpMethod.GET, method.httpMethod)

        assertEquals(2, method.requests.size)
        val request = method.requests[0]
        assertEquals("requestName", request.name)
        assertEquals("requestDescription", request.description)
        assertEquals("{ \"test\":\"test\" }\n", request.content)

        assertEquals(1, resource.childResources.size)
        val childResource = resource.childResources[0]
        assertEquals("childResourceName", childResource.name)
        assertEquals("childResourceDescription", childResource.description)
        assertEquals("childResourcePath", childResource.path)

    }

    @Test
    fun sync() {
        val project = loadProject("restserviceonly")
        val targetProject = ProjectBox.create(tempPath("sync").resolve(ProjectBox.FILE_NAME), "targetProjectName")
        targetProject.apply(SyncListener(project))
        assertFalse(targetProject.restServices.isEmpty())

        val targetRestService = targetProject.restServices[0]
        assertEquals("restserviceName", targetRestService.name)
        assertEquals("restserviceDescription", targetRestService.description)
        assertEquals("restserviceBasePath", targetRestService.basePath)

        assertEquals(1, targetRestService.resources.size)
        val targetResource = targetRestService.resources[0]
        assertEquals("resourceName", targetResource.name)
        assertEquals("resourceDescription", targetResource.description)
        assertEquals("resourcePath", targetResource.path)

        assertEquals(1, targetResource.methods.size)
        val targetMethod = targetResource.methods[0]
        assertEquals("methodName", targetMethod.name)
        assertEquals("methodDescription", targetMethod.description)
        assertEquals(SuuRestMethod.HttpMethod.GET, targetMethod.httpMethod)

    }

    companion object {
        fun loadProject(name: String): ProjectBox {
            val path = Paths.get("src", "test", "resources", "box", name, "project.box.yaml")
            return ProjectBox(Box(path))
        }

        fun tempPath(name: String): Path {
            val path = Paths.get("target", "boxes")
                .resolve(String.format("%s_%s", name, Instant.now().toString().replace(":", "_")))
            if (!Files.exists(path)) {
                Files.createDirectories(path)
            }
            return path
        }
    }
}