package eu.k5.soapui.swing

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.swing.karate.KarateExporterModel
import eu.k5.soapui.transform.karate.TransformationResult
import java.nio.file.Path
import javax.swing.DefaultListModel
import javax.swing.tree.MutableTreeNode


class MainModel {

    val currentProject = Observable<ProjectModel>()

    val projects = DefaultListModel<ProjectModel>()

    val currentTestCase = Observable<SuuTestCase>()

    val currentTransformationResult = Observable<TransformationResult>()

    val exporterModel = KarateExporterModel()


    class ProjectModel {
        var path: Path? = null
        var name: Observable<String> = Observable(null)
        var suProject = Observable<SuProject>(null)
        var structures = Observable<List<MutableTreeNode>>(null)

        override fun toString(): String {
            return name.getEntry() ?: "name"
        }
    }


    class TestCaseModel(
        val testCase: SuuTestCase
    ) {
        override fun toString(): String = testCase.name

    }

    class TestSuiteModel(
        val testSuite: SuuTestSuite
    ) {

        override fun toString(): String = testSuite.name
    }
}