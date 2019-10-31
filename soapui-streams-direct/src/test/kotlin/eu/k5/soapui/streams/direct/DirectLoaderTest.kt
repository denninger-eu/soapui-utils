package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.model.Project
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.lang.reflect.Method
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class DirectLoaderTest {


    @Test
    fun test() {

        val inputStream = Files.newInputStream(Paths.get("src", "test", "resources", "restproject-soapui-project.xml"))
        DirectLoader().bind(inputStream)


    }

    @Test
    fun `sync RestService`() {

        getTestProject("RestServiceOnly").use {
            val source = DirectLoader().bind(it)
            val target = Project()

            val listener = DirectSyncListener(source)
            target.apply(listener)


            val restService = target.getRestService("RestServiceName")

            assertNotNull(restService, "Rest service should have been created")
            assertEquals("DescriptionValue", restService.description)
            assertEquals("basePathValue", restService.basePath)
            //    println(DirectLoader.toXml(source as Project))

            println(" target " + DirectLoader.toXml(target))


        }
    }

    @Test
    fun `sync Resource`() {
        getTestProject("RestServiceWithResourceOnly").use {
            val source = DirectLoader().bind(it)
            val target = Project()

            val listener = DirectSyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())

            val resource = restService.resources[0]

            assertEquals("resourceDescription", resource.description)
            assertEquals("resourcePath", resource.path)
            assertEquals("resourceName", resource.name)

            println(" target " + DirectLoader.toXml(target))
        }
    }

    @Test
    fun `sync Method`() {
        getTestProject("RestServiceWithMethodOnly").use {
            val source = DirectLoader().bind(it)
            val target = Project()

            val listener = DirectSyncListener(source)
            target.apply(listener)

            val restService = target.getRestService("RestServiceName")

            println(" target " + DirectLoader.toXml(target))

            assertNotNull(restService, "Rest service should have been created")
            assertFalse(restService.resources.isEmpty())
            assertFalse(restService.resources[0].methods.isEmpty())

            val method = restService.resources[0].methods[0]
            assertEquals("methodDescription", method.description)
            assertEquals("methodName", method.name)
            assertEquals(SuuRestMethod.HttpMethod.GET, method.method)
        }
    }


    companion object {

        fun getTestProject(name: String): InputStream {
            val path = Paths.get("src", "test", "resources", "testcases", "$name-soapui-project.xml")

            require(Files.exists(path)) { "Testcase with name $name does not exist" }

            return Files.newInputStream(path)
        }
    }
}
