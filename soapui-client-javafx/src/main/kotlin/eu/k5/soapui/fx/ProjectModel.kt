package eu.k5.soapui.fx

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Component
import tornadofx.ScopedInstance
import tornadofx.onChange
import java.nio.file.Path

class ProjectModel : Component(), ScopedInstance {

    val projects: ObservableList<Project> = FXCollections.observableArrayList()
    val selected = SimpleObjectProperty<Project?>()
    val testcases: ObservableList<Testcase> = FXCollections.observableArrayList()
    val testcase = SimpleObjectProperty<Testcase?>()

    private var suuProject = SimpleObjectProperty<SuProject>()

    init {
        selected.onChange {
            if (it == null) {
                println("bind")
                suuProject.unbind()
                suuProject.set(null)
            } else {
                println("bind")
                suuProject.bind(it.suuProject)
            }
        }

        suuProject.onChange { suProject ->
            println("reset testcases")
            testcases.clear()
            suProject?.testSuites?.forEach { testSuite ->
                testcases.addAll(testSuite.testCases.map {
                    Testcase(
                        suProject.name,
                        testSuite.name,
                        it.name,
                        it
                    )
                })
            }
            println(testcases.size)
        }
    }

    class Project(path: Path) {
        val name = SimpleStringProperty(path.fileName.toString())
        val loading = SimpleObjectProperty(Loading.SCHEDULED)
        val path = ReadOnlyObjectWrapper(path)
        val suuProject: SimpleObjectProperty<SuProject> = SimpleObjectProperty()
    }

    class Testcase(
        projectName: String,
        testSuiteName: String,
        testCaseName: String,
        val suuTestcase: SuuTestCase
    ) {
        val projectName = SimpleStringProperty(projectName)
        val testSuiteName = SimpleStringProperty(testSuiteName)
        val testCaseName = SimpleStringProperty(testCaseName)
    }

    enum class Loading {
        SCHEDULED, LOADING, LOADED, FAILED
    }
}