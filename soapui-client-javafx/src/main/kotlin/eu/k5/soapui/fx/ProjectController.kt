package eu.k5.soapui.fx

import eu.k5.soapui.streams.direct.DirectLoader
import eu.k5.soapui.streams.model.SuProject
import tornadofx.Controller
import java.io.File
import java.nio.file.Files
import java.util.concurrent.Executors
import java.util.prefs.Preferences
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode

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

    fun updateOpenProjectFolder(file: File) {
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
            project.loading.value = ProjectModel.Loading.LOADING

            val suProject: SuProject = Files.newInputStream(project.path.value).use { DirectLoader().direct(it) }
            project.name.set(suProject.name)
            project.suuProject.value = suProject

            project.loading.value = ProjectModel.Loading.LOADED

/*            var structures = ArrayList<MutableTreeNode>()

            for (testSuite in suProject.testSuites) {
                val suiteNode = DefaultMutableTreeNode(MainModel.TestSuiteModel(testSuite))
                for (testCase in testSuite.testCases) {
                    suiteNode.add(DefaultMutableTreeNode(MainModel.TestCaseModel(testCase)))
                }
                structures.add(suiteNode)
            }
            println("project loaded")
            project.structures.update(structures)*/
        } catch (exception: Throwable) {
            println("Unalbe to load project" + exception.toString())
            exception.printStackTrace()

            project.loading.value = ProjectModel.Loading.FAILED
            throw exception
        }


    }
}
