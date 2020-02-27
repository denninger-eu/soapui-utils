package eu.k5.soapui.plugin.karate

import java.nio.charset.StandardCharsets
import java.nio.file.Files

class KarateExporterController(
    private val model: KarateExporterModel,
    private val view: KarateExporterView
) {

    fun doSaveInFolder() {

        val target = view.getTargetFolder()
        if (target != null) {
            val root = target.toPath()
            for (artifact in model.artifacts.elements()) {
                Files.newOutputStream(root.resolve(artifact.name)).use {
                    it.write(artifact.content.toByteArray(StandardCharsets.UTF_8))
                }
            }
        }
    }

    fun doSaveZip() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}