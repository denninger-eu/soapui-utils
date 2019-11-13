package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.ProjectBox
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant

abstract class AbstractDirectTest {

    companion object {
        fun loadFromBox(name: String): ProjectBox {
            val path =
                Paths.get("..", "soapui-streams-test", "src", "main", "resources", "examples", name, "project.box.yaml")
            return ProjectBox(Box(path))
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