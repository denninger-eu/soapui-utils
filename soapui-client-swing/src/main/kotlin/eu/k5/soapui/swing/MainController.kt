package eu.k5.soapui.swing

import eu.k5.soapui.streams.direct.DirectLoader
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.swing.karate.KarateExporterModel
import eu.k5.soapui.transform.karate.KarateTransformer
import eu.k5.soapui.transform.karate.TransformationResult
import java.nio.file.Files
import java.util.concurrent.Executors
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode
import javax.swing.tree.TreeNode

class MainController(
    private val model: MainModel,
    private val view: MainView
) {

    private val executor = Executors.newSingleThreadExecutor()

    init {
        model.currentTestCase.register() { _, new -> updateTestCase(new) }
        model.currentTransformationResult.register { old, new -> updateTransformationResult(new) }
    }

    private fun updateTransformationResult(result: TransformationResult?) {
        if (result == null) {
            return
        }
        println("Updating exporter")
        model.exporterModel.artifacts.clear()
        for (artifact in result.artifacts) {
            model.exporterModel.artifacts.addElement(KarateExporterModel.Artifact(artifact.name, artifact.content))
        }
    }

    private fun updateTestCase(testCase: SuuTestCase?) {
        if (testCase == null) {
            model.currentTransformationResult.update(null)
            return
        }
        val transform = KarateTransformer().transform(testCase)

        println("setting transformation")
        model.currentTransformationResult.update(transform)
    }

    fun doAddProject() {
        val file = view.openProject() ?: return

        val project = MainModel.ProjectModel()
        project.path = file.toPath()
        project.name.update("loading .. " + project.path?.fileName.toString())

        model.projects.addElement(project)
        executor.submit() { loadProject(project) }
    }


    private fun loadProject(project: MainModel.ProjectModel) {
        println("Loading soapui project")
        try {
            val suProject: SuProject = Files.newInputStream(project.path).use { DirectLoader().direct(it) }
            project.name.update(suProject.name)
            project.suProject.update(suProject)

            var structures = ArrayList<MutableTreeNode>()

            for (testSuite in suProject.testSuites) {
                val suiteNode = DefaultMutableTreeNode(MainModel.TestSuiteModel(testSuite))
                for (testCase in testSuite.testCases) {
                    suiteNode.add(DefaultMutableTreeNode(MainModel.TestCaseModel(testCase)))
                }
                structures.add(suiteNode)
            }
            println("project loaded")
            project.structures.update(structures)
        } catch (exception: Throwable) {
            println("Unalbe to load project" + exception.toString())
            exception.printStackTrace()
            throw exception
        }


    }
}
