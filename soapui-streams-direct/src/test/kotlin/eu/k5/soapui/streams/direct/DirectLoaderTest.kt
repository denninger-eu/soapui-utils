package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.sync.SyncListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class DirectLoaderTest {


    @Test
    fun test() {

        val inputStream = Files.newInputStream(Paths.get("src", "test", "resources", "restproject-soapui-project.xml"))
      // DirectLoader().bind(inputStream)


    }

    @Test
    fun `sync RestService`() {

        getTestProject("RestServiceOnly").use {
           // val source = DirectLoader().bind(it)

/*            val listener = SyncListener(source)
            target.apply(listener)


            val restService = target.getRestService("RestServiceName")

            assertNotNull(restService, "Rest service should have been created")
            assertEquals("DescriptionValue", restService.description)
            assertEquals("basePathValue", restService.basePath)
            //    println(DirectLoader.toXml(source as Project))

            println(" target " + target.toXml())*/


        }
    }

/*    @Test
    fun `sync Resource`() {
        getTestProject("RestServiceWithResourceOnly").use {
            val source = DirectLoader().bind(it)
            val target = Project()

            val listener = SyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())

            val resource = restService.resources[0]

            assertEquals("resourceDescription", resource.description)
            assertEquals("resourcePath", resource.path)
            assertEquals("resourceName", resource.name)

            println(" target " + target.toXml())
        }
    }*/

/*    @Test
    fun `sync Resource into RestService`() {
    //    val target = getTestProject("RestServiceOnly").use { DirectLoader().bind(it) } as Project

        getTestProject("RestServiceWithResourceOnly").use {
            val source = DirectLoader().bind(it)

            val listener = SyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())

            val resource = restService.resources[0]

            println("target " + target.toXml())

            assertEquals("resourceDescription", resource.description)
            assertEquals("resourcePath", resource.path)
            assertEquals("resourceName", resource.name)


        }
    }*/

    @Test
    fun `sync Method into Resource`() {
        val target = testProject("RestServiceWithResourceOnly")
        val source = testProject("RestServiceWithMethodOnly")

        target.apply(SyncListener(source))
        val restService = target.getRestService("RestServiceName")

        assertNotNull(restService, "Rest service should have been created")
        assertFalse(restService.resources.isEmpty())
        assertFalse(restService.resources[0].methods.isEmpty(), "Method should have been created")

        val method = restService.resources[0].methods[0]
        assertEquals("methodDescription", method.description)
        assertEquals("methodName", method.name)
        assertEquals(SuuRestMethod.HttpMethod.GET, method.httpMethod)

    }

    @Test
    fun `sync ChildResource into Resource`() {
        val target = testProject("RestServiceWithResourceOnly")
        val source = testProject("RestServiceWithChildResource")

        target.apply(SyncListener(source))
        val restService = target.getRestService("RestServiceName")

        assertNotNull(restService, "Rest service should have been created")
        assertFalse(restService.resources.isEmpty())
        assertFalse(restService.resources[0].childResources.isEmpty(), "No child resource found")
        val resource = restService.resources[0].childResources[0]

        assertEquals("childResourceDescription", resource.description)
        assertEquals("childResourcePath", resource.path)
        assertEquals("childResourceName", resource.name)
    }

    @Test
    @Disabled("Remove usage of Project")
    fun `sync ChildResource`() {
        getTestProject("RestServiceWithChildResource").use {
            val source = DirectLoader().load(it)
            val target = Project()

            val listener = SyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())
            assertFalse(restService.resources[0].childResources.isEmpty(), "No child resource found")
            val resource = restService.resources[0].childResources[0]
            println(" target " + target.toXml())

            assertEquals("childResourceDescription", resource.description)
            assertEquals("childResourcePath", resource.path)
            assertEquals("childResourceName", resource.name)

        }
    }

    @Test
    @Disabled("Remove usage of Project")
    fun `sync Method`() {
        getTestProject("RestServiceWithMethodOnly").use {
            val source = DirectLoader().load(it)
            val target = Project()

            val listener = SyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            println(" target " + target.toXml())

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())
            assertFalse(restService.resources[0].methods.isEmpty())

            val method = restService.resources[0].methods[0]
            assertEquals("methodDescription", method.description)
            assertEquals("methodName", method.name)
            assertEquals(SuuRestMethod.HttpMethod.GET, method.httpMethod)
        }
    }


    @Test
    @Disabled("Remove usage of Project")
    fun `sync Request`() {
        getTestProject("RestServiceWithRequest").use {
            val source = DirectLoader().load(it)
            val target = Project()

            val listener = SyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            println(" target " + target.toXml())

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())
            assertFalse(restService.resources[0].methods.isEmpty())
            assertFalse(restService.resources[0].methods[0].requests.isEmpty())

            val request = restService.resources[0].methods[0].requests[0]
            assertEquals("requestDescription", request.description)
            assertEquals("requestName", request.name)

        }
    }

    @Test
    fun `sync Request into Method`() {

        val source = testProject("RestServiceWithRequest")
        val target = testProject("RestServiceWithMethodOnly")

        target.apply(SyncListener(source))
        val restService = target.getRestService("RestServiceName")

        assertNotNull(restService, "Rest service should have been created")
        assertFalse(restService.resources.isEmpty())
        assertFalse(restService.resources[0].methods.isEmpty())
        assertFalse(restService.resources[0].methods[0].requests.isEmpty())

        val request = restService.resources[0].methods[0].requests[0]
        assertEquals("requestDescription", request.description)
        assertEquals("requestName", request.name)
    }

    @Test
    fun `sync Change`() {

        val source = testProject("RestServiceWithRequestChanged")
        val target = testProject("RestServiceWithRequest")

        target.apply(SyncListener(source))
        val restService = target.getRestService("RestServiceName")

        assertNotNull(restService, "Rest service should have been created")
        assertFalse(restService.resources.isEmpty())
        assertFalse(restService.resources[0].methods.isEmpty())
        assertFalse(restService.resources[0].methods[0].requests.isEmpty())

        val request = restService.resources[0].methods[0].requests[0]
        assertEquals("requestDescriptionChanged", request.description)
        assertEquals("requestName", request.name)

        assertEquals("DescriptionValueChanged", restService.description)
        assertEquals("resourceDescriptionChanged", restService.resources[0].description)

    }


    companion object {

        fun testProject(name: String): SuProject {
            return getTestProject(name).use { DirectLoader().load(it) }
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
