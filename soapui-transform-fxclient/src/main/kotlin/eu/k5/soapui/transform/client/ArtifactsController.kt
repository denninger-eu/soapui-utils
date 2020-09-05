package eu.k5.soapui.transform.client

import javafx.collections.FXCollections
import tornadofx.Controller
import tornadofx.Scope
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files


class ArtifactsController(

) : Controller() {
    override val scope = super.scope as ArtifactsScope
    private val model = scope.model


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