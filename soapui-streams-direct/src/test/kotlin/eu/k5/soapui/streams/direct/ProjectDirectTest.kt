package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.ProjectBox
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.direct.model.TestStepDelayDirect
import eu.k5.soapui.streams.listener.resource.DirectSyncListener
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.streams.tests.SuuAssert
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ProjectDirectTest {


    @Test
    fun syncTest() {
        val testProject = getTestProject("RestServiceComplete").use { DirectLoader().direct(it) }

        val folder = Paths.get("target", "box", Instant.now().toString().replace(":", "_"))
        Files.createDirectories(folder)

        val project = ProjectBox.create(folder.resolve(ProjectBox.FILE_NAME), "name")
        Suu.syncRestService(testProject, project, testProject.restServices[0].name!!)
    }

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

    @Test
    fun readTestSuiteProject() {
        val project = testProject("TestSuiteProject")

        assertEquals(2, project.testSuites.size)
        val testSuite = project.getTestSuite("TestSuiteName")

        assertFalse(project.getTestSuite("TestSuiteDisabled")!!.enabled)

        assertNotNull(testSuite)
        assertEquals("TestSuiteName", testSuite.name)
        assertTrue(testSuite.enabled)

        assertEquals(2, testSuite.testCases.size)

        assertFalse(testSuite.getTestCase("TestCaseDisabled")!!.enabled)

        assertTestCase(testSuite.getTestCase("TestCaseName")!!)
    }

    private fun assertTestCase(testCase: SuuTestCase) {
        assertEquals("TestCaseName", testCase.name)
        assertTrue(testCase.enabled)

        val step = testCase.steps

        val delayStep = testCase.getStep("DelayStepName")
        assertNotNull(delayStep)
        assertTrue(delayStep is TestStepDelayDirect)
        assertTrue(delayStep.enabled)
        assertEquals("DelayStepName", delayStep.name)
        assertEquals("DelayStepDescription", delayStep.description)
        assertEquals(1000, delayStep.delay)

        val delayStepDisabled = testCase.getStep("DelayStepDisabled")
        assertFalse(delayStepDisabled!!.enabled)

        assertTestStepPropertyTransfer(testCase.getStep("PropertyTransferName") as SuuTestStepPropertyTransfers)
    }


    private fun assertTestStepPropertyTransfer(propertyTransfer: SuuTestStepPropertyTransfers) {
        assertEquals("PropertyTransferName", propertyTransfer.name)
        assertEquals("PropertyTransferDescription", propertyTransfer.description)
        assertTrue(propertyTransfer.enabled)

        assertEquals(1, propertyTransfer.transfers.size)
        val transfer = propertyTransfer.transfers[0]

        assertEquals("transferName", transfer.name)
        assertTrue(transfer.enabled)

        assertEquals(SuuPropertyTransfer.Language.XPATH, transfer.source.language)
        assertEquals(SuuPropertyTransfer.Language.JSONPATH, transfer.target.language)

        assertEquals("sourceTransferExpression", transfer.source.expression)
        assertEquals("targetTransferExpression", transfer.target.expression)
        assertEquals("ProjectPropertyName", transfer.source.propertyName)
        assertEquals("TestSuitePropertyName", transfer.target.propertyName)
        assertEquals("#Project#", transfer.source.stepName)
        assertEquals("#TestSuite#", transfer.target.stepName)

        val suuPropertyTransfer = propertyTransfer.transfers[2]
        assertEquals("transferNameDisabled", suuPropertyTransfer.name)
        assertFalse(suuPropertyTransfer.enabled)

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