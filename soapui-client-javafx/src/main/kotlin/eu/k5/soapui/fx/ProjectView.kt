package eu.k5.soapui.fx

import eu.k5.soapui.transform.client.GeneratorController
import javafx.beans.property.SimpleBooleanProperty
import javafx.stage.FileChooser
import tornadofx.*

class ProjectView : View() {

    private val model: ProjectModel by inject()
    private val controller: ProjectController by inject()
    private val moduleController: GeneratorController by inject()

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
                moduleController.test()
            }
            button() {
                graphic = Graphics.REMOVE.image
                enableWhen() { SimpleBooleanProperty(false) }
            }

            button() {
                graphic = Graphics.REFRESH.image
                enableWhen() { SimpleBooleanProperty(false) }
            }
        }
        tableview(model.projects) {
            column(title = "Name", ProjectModel.Project::name).remainingWidth()
            column("Loading", ProjectModel.Project::loading)
            smartResize()
            onSelectionChange {
                model.selected.value = it
            }
        }

        label {}
        label {
            text = "Testcases"
        }
        buttonbar {

            button("Karate") {
                enableWhen() { model.testcase.isNotNull }
            }.action {
                controller.generateKarate()

            }
            button("Restassured") {
                enableWhen() { model.testcase.isNotNull }
            }.action {
                controller.generateRestassured()

            }
        }
        tableview(model.testcases) {
            column(title = "Project", ProjectModel.Testcase::projectName)
            column(title = "Suite", ProjectModel.Testcase::testSuiteName)
            column(title = "Case", ProjectModel.Testcase::testCaseName)

            onSelectionChange {
                model.testcase.value = it
            }
        }
    }


}