package eu.k5.soapui.transform.client

import javafx.collections.FXCollections
import tornadofx.Controller
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files


class ArtifactsController(

) : Controller() {
    private val model: ArtifactsModel by inject()

    fun saveAsZip() {
    }

    fun saveInDirectory(target: File?) {
        if (target == null) {
            return
        }

        val root = target.toPath()
        for (artifact in model.artifacts) {
            Files.newOutputStream(root.resolve(artifact.nameProperty.get())).use {
                it.write(artifact.contentProperty.get().toByteArray(StandardCharsets.UTF_8))
            }
        }
    }


}