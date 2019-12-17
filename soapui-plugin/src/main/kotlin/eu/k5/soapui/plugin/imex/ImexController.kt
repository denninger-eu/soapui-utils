package eu.k5.soapui.plugin.imex

import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.box.ProjectBox
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.model.SuProject
import java.lang.UnsupportedOperationException
import java.nio.file.Files
import java.nio.file.Paths


class ImexController(
    val model: ImexModel
) {

    init {
        val projectConfig = model.config.createIfAbsent(model.project.name)

        model.folder.registerOnEdt { folderChanged(it) }
        if (projectConfig != null) {
            model.folder.update(projectConfig.base)
        }
        model.target.registerOnEdt { targetChanged(it) }
    }

    private fun folderChanged(folder: String?) {
        println("Folder changed: $folder")
        model.target.update(null)
        if (folder != null) {
            val box = resolveExisting()
            model.target.update(box)
        }
    }

    private fun targetChanged(target: SuProject?) {
        if (target == null) {
            model.exportEnabled.update(false)
            model.importEnabled.update(false)
            if (model.folder.getEntry() != null) {
                model.createEnabled.update(true)
            }
        } else {
            model.exportEnabled.update(true)
            model.importEnabled.update(true)
            model.createEnabled.update(false)
        }

    }

    fun cancel() {
    }

    fun doImport() {
        val box = model.target.getEntry() as ProjectBox ?: return
        println("DoExport: " + box.fileName())
        val projectDirect = ProjectDirect(model.project)
        Suu.sync(box, projectDirect)
    }

    private fun resolveExisting(): ProjectBox? {
        val folder = model.folder.getEntry()
        println("loading from folder: $folder")
        val path = Paths.get(folder)

        if (Files.isDirectory(path)) {
            val projectPath = path.resolve(ProjectBox.FILE_NAME)
            if (Files.exists(projectPath)) {
                return ProjectBox.load(path)
            } else {
                return null
            }
        } else if (path.fileName.toString() == ProjectBox.FILE_NAME) {
            return ProjectBox.load(path)
        }
        return null
    }

    private fun resolveExistingOrCreate(): ProjectBox {
        val folder = model.folder.getEntry()
            ?: throw UnsupportedOperationException("Unable to create in 'null' folder")
        val path = Paths.get(folder)

        if (Files.isDirectory(path)) {
            val projectPath = path.resolve(ProjectBox.FILE_NAME)
            if (Files.exists(projectPath)) {
                return ProjectBox.load(path)
            }
            return ProjectBox.create(projectPath, model.project.name)
        }
        if (!Files.exists(path)) {
            Files.createDirectories(path)
        }
        val projectPath = path.resolve(ProjectBox.FILE_NAME)
        return ProjectBox.create(projectPath, model.project.name)
    }


    fun doExport() {
        val box = model.target.getEntry() as ProjectBox ?: return

        println("DoExport: " + box.fileName())
        val projectDirect = ProjectDirect(model.project)
        Suu.sync(projectDirect, box)
    }

    fun doCreate() {
        val box = resolveExistingOrCreate()
        println("DoExport: " + box.fileName())
        val projectDirect = ProjectDirect(model.project)
        Suu.sync(projectDirect, box)
    }

}