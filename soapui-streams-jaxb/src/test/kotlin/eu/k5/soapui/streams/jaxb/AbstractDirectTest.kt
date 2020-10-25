package eu.k5.soapui.streams.jaxb

import eu.k5.soapui.streams.jaxb.model.ProjectJaxb
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class AbstractJaxbTest {

    companion object {


        private fun searchRoot(): Path {
            var path: Path? = Paths.get(".").toAbsolutePath()

            for (level in 0..10) {
                if (Files.exists(path?.resolve("soapui-streams-test"))) {
                    return path!!
                }
                path = path?.parent
                if (path == null) {
                    throw IllegalStateException("unable to find project root")
                }
            }
            throw IllegalStateException("unable to find project root, max depth")
        }


        fun testProject(name: String): ProjectJaxb {
            return openTestProjectInputStream(name).use { JaxbLoader().load(it) }
        }

        fun openTestProjectInputStream(name: String): InputStream {
            val path = Paths.get("src", "test", "resources", "testcases", "$name-soapui-project.xml")
            require(Files.exists(path)) { "Testcase with name $name does not exist" }

            return Files.newInputStream(path)
        }


    }
}