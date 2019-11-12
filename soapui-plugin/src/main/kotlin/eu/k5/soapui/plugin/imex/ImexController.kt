package eu.k5.soapui.plugin.imex

import eu.k5.soapui.plugin.SuuConfig
import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.box.ProjectBox
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Paths


class ImexController(
    private val view: ImexView,
    private val model: ImexModel
) {

    fun init() {
        val projectConfig = model.config.createIfAbsent(model.project.name)

        if (projectConfig != null) {
            model.folder = projectConfig.base
        }
    }


    fun cancel() {
    }


    fun doImport() {

    }

    private fun resolveBox(): ProjectBox {

        val folder = model.folder
        val path = Paths.get(folder)

        if (Files.isDirectory(path)) {
            val projectPath = path.resolve(ProjectBox.FILE_NAME)
            if (Files.exists(projectPath)) {
                return ProjectBox.load(path)
            } else {
                return ProjectBox.create(projectPath, model.project.name)
            }
        } else if (path.fileName.toString() == ProjectBox.FILE_NAME) {
            return ProjectBox.load(path)
        }
        throw IllegalStateException("")
    }

    fun doExport() {
        println("DoExport")
        val box = resolveBox()

        Suu.syncRestService(model.project, box, model.restService!!.name!!)
    }

}