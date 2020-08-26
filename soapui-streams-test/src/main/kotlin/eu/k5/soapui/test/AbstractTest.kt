package eu.k5.soapui.test

import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.ProjectBox
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class AbstractTest {
    companion object {


        fun loadFromBox(name: String): ProjectBox {
            val path = searchRoot().resolve("soapui-streams-test").resolve("src").resolve("main").resolve(
                "resources"
            ).resolve("examples").resolve(name).resolve("project.box.yaml")
            return ProjectBox(BoxImpl(path))
        }


        private fun searchRoot(): Path {
            var path: Path? = Paths.get(".").toAbsolutePath()


            for (x in 0..10) {
                if (Files.exists(path?.resolve("soapui-utils"))) {
                    return path!!.resolve("soapui-utils")
                }
                if (Files.exists(path?.resolve("soapui-streams-test"))) {
                    return path!!
                }
                path = path?.parent
                if (path == null) {

                    val cwd = Paths.get(".").toAbsolutePath()
                    throw IllegalStateException("unable to find project root, started ${cwd}")
                }
            }
            throw IllegalStateException("unable to find project root, max depth")
        }

    }
}