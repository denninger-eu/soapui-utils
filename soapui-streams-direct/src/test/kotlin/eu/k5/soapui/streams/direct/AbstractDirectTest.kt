package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.ProjectBox
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant

abstract class AbstractDirectTest {

    companion object {
        fun loadFromBox(name: String): ProjectBox {

            val root = searchRoot()

            val path = searchRoot().resolve("soapui-streams-test").resolve("src").resolve("main").resolve(
                "resources"
            ).resolve("examples").resolve(name).resolve("project.box.yaml")
            return ProjectBox(Box(path))
        }

        private fun searchRoot(): Path {
            var path: Path? = Paths.get(".").toAbsolutePath()

            for (x in 0..10) {
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

        fun createTempProjectBox(name: String): ProjectBox {
            val tempPath = tempPath(name).resolve(ProjectBox.FILE_NAME)
            return ProjectBox.create(tempPath, name)
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