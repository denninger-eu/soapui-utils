package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.ProjectBox
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.listener.resource.DirectSyncListener
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.tests.SuuAssert
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProjectDirectTest {


    @Test
    fun writeRestProject() {


        val project = DirectLoader().newProject()


        val restService = project.createRestService("restServiceName")
        restService.description = "description"
        restService.basePath = "basePath"

        val resource = restService.createResource("newResourceName", "newResourcePath")

        val method = resource.createMethod("methodName")

        val request = method.createRequest("requestName")

        val storedProject = storeAndReopen("write", project)

    }

    @Test
    fun writeProjectWithSync() {
        val box = loadFromBox("complete")

        val project = DirectLoader().newProject().apply(DirectSyncListener(box)) as ProjectDirect
        SuuAssert().assertEquals(project, box)

        val storedProject = storeAndReopen("withsync", project)
        SuuAssert().assertEquals(storedProject, box)
    }

    @Test
    fun readRestProject() {

        val project = testProject("RestServiceComplete")


        val resource = project.restServices[0].resources[0]

        assertEquals("resourceName", resource.name)
        assertEquals(2, resource.parameters.parameters.size)

        assertMethod(resource.methods[0])
    }

    private fun assertMethod(restMethod: SuuRestMethod) {
        assertEquals("methodName", restMethod.name)

        val parameters = restMethod.parameters
        assertNotNull(parameters)
        assertEquals(1, parameters.parameters.size)
        val parameter = parameters.byName("methodParameterName")
        assertNotNull(parameter)
        assertEquals("methodParameterValue", parameter.value)
        assertEquals(SuuRestParameter.Style.HEADER, parameter.style)

        val restRequest = restMethod.requests[0]
        assertRequest(restRequest)
    }

    private fun assertRequest(restRequest: SuuRestRequest) {
        assertEquals("requestName", restRequest.name)

        val parameters = restRequest.parameters
        assertNotNull(parameters)
        assertEquals(1, parameters.parameters.size)
        val parameter = parameters.byName("requestParameterName")
        assertNotNull(parameter)
        assertEquals("requestParameterValue", parameter.value)
        assertEquals(SuuRestParameter.Style.QUERY, parameter.style)

    }

    companion object {

        fun loadFromBox(name: String): ProjectBox {
            val path =
                Paths.get("..", "soapui-streams-test", "src", "main", "resources", "examples", name, "project.box.yaml")
            return ProjectBox(Box(path))
        }

        fun storeAndReopen(scope: String, projectDirect: ProjectDirect): ProjectDirect {
            val file = tempPath(scope).resolve("project.xml")
            projectDirect.save(file)
            return Files.newInputStream(file).use { DirectLoader().direct(it) }
        }

        fun testProject(name: String): ProjectDirect {
            return getTestProject(name).use { DirectLoader().direct(it) }
        }

        fun getTestProject(name: String): InputStream {
            val path = Paths.get("src", "test", "resources", "testcases", "$name-soapui-project.xml")

            require(Files.exists(path)) { "Testcase with name $name does not exist" }

            return Files.newInputStream(path)
        }

        private fun tempPath(name: String): Path {
            val path = Paths.get("target", "projects")
                .resolve(String.format("%s_%s", name, Instant.now().toString().replace(":", "_")))
            if (!Files.exists(path)) {
                Files.createDirectories(path)
            }
            return path
        }


    }
}