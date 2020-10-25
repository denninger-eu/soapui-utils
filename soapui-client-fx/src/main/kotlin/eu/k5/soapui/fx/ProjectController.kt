package eu.k5.soapui.fx

import eu.k5.soapui.streams.direct.DirectLoader
import eu.k5.soapui.streams.jaxb.JaxbLoader
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.transform.client.GenerateTestcaseEvent
import eu.k5.tolerantxml.client.repair.CreateTolerantConverter
import tornadofx.Controller
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.util.concurrent.Executors
import java.util.prefs.Preferences

class ProjectController : Controller() {

    private val model: ProjectModel by inject()
    private val executor = Executors.newSingleThreadExecutor()


    fun getOpenProjectFolder(): File? {
        val prefs: Preferences = Preferences.userNodeForPackage(ProjectView::class.java)
        val value = prefs.get("projectFolder", "")
        val folder = File(value)
        if (folder.exists()) {
            return folder
        } else {
            return null
        }
    }

    private fun updateOpenProjectFolder(file: File) {
        val prefs: Preferences = Preferences.userNodeForPackage(ProjectView::class.java)
        prefs.put("projectFolder", file.canonicalPath.toString())
    }


    fun openProject(files: List<File>) {
        if (files.isEmpty()) {
            return
        }
        val file = files[0]
        if (file.exists()) {
            updateOpenProjectFolder(file.parentFile)
        }
        val newProject = ProjectModel.Project(file.toPath())
        model.projects.add(newProject)
        executor.submit() { loadProject(newProject) }
    }


    private fun loadProject(project: ProjectModel.Project) {
        println("Loading soapui project")
        try {
            project.state.value = ProjectModel.State.LOADING

            val suProject: SuProject = Files.newInputStream(project.path.value).use { load(it) }
            project.name.set(suProject.name)
            project.suuProject.value = suProject
            project.state.value = ProjectModel.State.LOADED
        } catch (exception: Throwable) {
            println("Unable to load project$exception")
            exception.printStackTrace()
            project.state.value = ProjectModel.State.FAILED
        }
    }

    private fun load(inputStream: InputStream): SuProject {
        return JaxbLoader().load(inputStream)
        //return DirectLoader().direct(inputStream)
    }

    fun generateKarate() {
        val testcase = model.testcase.get()
        println("event karate")

        if (testcase != null) {

            val event = GenerateTestcaseEvent(testcase.suuTestcase, "karate") { fire(NewTabEvent(it)) }
            fire(event)

        } else {
            println("null karate")
        }

    }

    fun generateRestassured() {
        val testcase = model.testcase.get()
        println("event restassured")
        if (testcase != null) {
            val event = GenerateTestcaseEvent(testcase.suuTestcase, "restassured") { fire(NewTabEvent(it)) }
            fire(event)

        } else {
            println("null ra")
        }
    }

    fun startRepair() {
        val definition = model.webservice.value?.suuWsdlService?.definition
        if (definition == null) {
            println("No definition")
            return
        }

        val event = CreateTolerantConverter(
            model.webservice.value?.wsdlServiceName?.get() ?: "unknown",
            definition.parts[0].url.replace('\\', '/')
        )
        for (part in definition.parts) {
            event.xsds[part.url.replace('\\', '/')] = part.content
        }
        fire(event)
    }
}
