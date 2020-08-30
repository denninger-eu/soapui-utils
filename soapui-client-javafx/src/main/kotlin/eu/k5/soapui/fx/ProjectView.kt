package eu.k5.soapui.fx

import com.eviware.soapui.support.ExtensionFileFilter
import javafx.scene.Parent
import javafx.stage.FileChooser
import tornadofx.*
import java.util.prefs.Preferences

class ProjectView : View() {

    private val model: ProjectModel by inject()
    private val controller: ProjectController by inject()

    override val root = vbox {

        label {
            text = "Projects"
        }
        buttonbar {
            button() {
                graphic = Graphics.ADD.image
            }.action {
                val initDirectory = controller.getOpenProjectFolder()

                val files = chooseFile(
                    title = "SoapUi Project",
                    filters = arrayOf(
                        FileChooser.ExtensionFilter("SoapUi (*.xml)", "*.xml"),
                        FileChooser.ExtensionFilter("All files", "*")
                    ),
                    initialDirectory = initDirectory,
                    mode = FileChooserMode.Single
                )
                controller.openProject(files)

            }
            button() {
                graphic = Graphics.REMOVE.image
            }
        }
        tableview(model.projects) {
            column(title = "Name", ProjectModel.Project::name).remainingWidth()
            column("Loading", ProjectModel.Project::loading)
            smartResize()
            
        }

        label {}
        label {
            text = "Testcases"
        }
        buttonbar {
            button() {
                graphic = Graphics.REFRESH.image
            }
            button("Karate") {

            }
            button("Restassured") {

            }
        }
        tableview(model.testcases) {

        }
    }


}