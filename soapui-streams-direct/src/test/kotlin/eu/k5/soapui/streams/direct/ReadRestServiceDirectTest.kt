package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.ProjectBox
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.listener.difference.DifferenceListener
import eu.k5.soapui.streams.listener.sync.SyncListener
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestService
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ReadRestServiceDirectTest : AbstractDirectTest() {


/*
    @Test
    fun syncTest() {
        val testProject = getTestProject("RestServiceComplete").use { DirectLoader().direct(it) }

        val folder = Paths.get("target", "box", Instant.now().toString().replace(":", "_"))
        Files.createDirectories(folder)

        val project = ProjectBox.create(folder.resolve(ProjectBox.FILE_NAME), "name")
        Suu.syncRestService(testProject, project, testProject.restServices[0].name!!)
    }
*/

/*    @Test
    fun writeRestProject() {


        val project = DirectLoader().newProject()


        val restService = project.createRestService("restServiceName")
        restService.description = "description"
        restService.basePath = "basePath"

        val resource = restService.createResource("newResourceName", "newResourcePath")

        val method = resource.createMethod("methodName")

        val request = method.createRequest("requestName")

        val storedProject = storeAndReopen("write", project)

    }*/

/*    @Test
    fun writeProjectWithSync() {
        val box = loadFromBox("complete")

        val project = DirectLoader().newProject().apply(SyncListener(box)) as ProjectDirect
        SuuAssert().assertEquals(project, box)

        val storedProject = storeAndReopen("withsync", project)
        SuuAssert().assertEquals(storedProject, box)
    }*/

    @Test
    fun writeBoxFromTestSuite() {
        val project = testProject("RestServiceComplete")

        val box = createTempProjectBox("RestServiceComplete")
        val apply = box.apply(SyncListener(project))

        val firstDifference = DifferenceListener(project)

        assertTrue(firstDifference.differences.isEmpty(), firstDifference.differences.toString())

        // double apply should not affect differences
        box.apply(SyncListener(project))

        val secondDifference = DifferenceListener(project)
        box.apply(secondDifference)

        assertTrue(secondDifference.differences.isEmpty(), secondDifference.differences.toString())

        assertRestService(box.restServices[0])
    }

    @Test
    fun readRestProject() {

        val project = testProject("RestServiceComplete")

        assertRestService(project.restServices[0])
    }

    private fun assertRestService(restService: SuuRestService) {
        assertEquals("RestServiceName", restService.name)
        assertEquals("DescriptionValue", restService.description)
        assertEquals("basePathValue", restService.basePath)

        assertTrue(restService.endpoints.contains("http://example.com"))
        assertTrue(restService.endpoints.contains("http://example.net"))


        val resource = restService.resources[0]

        assertEquals("resourceName", resource.name)
        assertEquals(2, resource.parameters.allParameters.size)


        assertMethod(resource.methods[0])
    }


    private fun assertMethod(restMethod: SuuRestMethod) {
        assertEquals("methodName", restMethod.name)

        val parameters = restMethod.parameters
        assertNotNull(parameters)
        assertEquals(2, parameters.parameterOwning.size)
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
        assertEquals(4, parameters.allParameters.size)
        assertEquals(0, parameters.parameterOwning.size)
        assertEquals(2, parameters.parameterOverride.size)
        val parameter = parameters.byName("requestParameterName")
        assertNotNull(parameter)
        assertTrue(parameter.isOverride())
        assertEquals("requestParameterValue", parameter.value)
        assertEquals(SuuRestParameter.Style.QUERY, parameter.style)

    }

    companion object {

        fun loadFromBox(name: String): ProjectBox {
            val path =
                Paths.get("..", "soapui-streams-test", "src", "main", "resources", "examples", name, "project.box.yaml")
            return ProjectBox(BoxImpl(path))
        }

        fun storeAndReopen(scope: String, projectDirect: ProjectDirect): ProjectDirect {
            val file = tempPath(scope).resolve("project.xml")
            projectDirect.save(file)
            return Files.newInputStream(file).use { DirectLoader().direct(it) }
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