package eu.k5.soapui.fx

import eu.k5.soapui.streams.model.SuProject
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Component
import tornadofx.ScopedInstance
import java.nio.file.Path

class ProjectModel : Component(), ScopedInstance {

    val projects: ObservableList<Project> = FXCollections.observableArrayList()
    val testcases: ObservableList<Testcase> = FXCollections.observableArrayList()


    class Project(path: Path) {
        val name = SimpleStringProperty(path.fileName.toString())
        val loading = SimpleObjectProperty(Loading.SCHEDULED)
        val path = ReadOnlyObjectWrapper(path)
        val suuProject: SimpleObjectProperty<SuProject> = SimpleObjectProperty()
    }

    class Testcase() {

    }

    enum class Loading {
        SCHEDULED, LOADING, LOADED, FAILED
    }
}