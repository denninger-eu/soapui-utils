package eu.k5.soapui.fx

import javafx.beans.property.SimpleBooleanProperty
import javafx.stage.FileChooser
import tornadofx.*

class ProjectView : View() {

    private val model: ProjectModel by inject()
    private val controller: ProjectController by inject()

    override val root = vbox {


        buttonbar {
            label("Projects ")
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
                enableWhen(SimpleBooleanProperty(false))
            }

            button() {
                graphic = Graphics.REFRESH.image
                enableWhen(SimpleBooleanProperty(false))
            }
        }
        tableview(model.projects) {
            column(title = "Name", ProjectModel.Project::name).remainingWidth()
            column("State", ProjectModel.Project::state)
            smartResize()
            onSelectionChange {
                model.selected.value = it
            }
        }

        tabpane {
            tab<Testcases>()
            tab<Webservices>()
        }
    }

    class Testcases : View() {
        private val model: ProjectModel by inject()
        private val controller: ProjectController by inject()

        override val root = vbox {
            title = "Testcases"
            closeableWhen() { SimpleBooleanProperty(false) }
            model.testcases.onChange { visibleProperty().set(!model.testcases.isEmpty()) }
            val label = label("Project")
            model.selected.onChange { label.text = it?.name?.get() ?: "" }
            buttonbar {

                button("Karate") {
                    enableWhen() { model.testcase.isNotNull }
                }.action {
                    controller.generateKarate()

                }
                button("Restassured") {
                    enableWhen(model.testcase.isNotNull)
                }.action {
                    controller.generateRestassured()

                }
            }


            tableview(model.testcases) {
                column(title = "Suite", ProjectModel.Testcase::testSuiteName)
                column(title = "Case", ProjectModel.Testcase::testCaseName).remainingWidth()
                smartResize()
                onSelectionChange {
                    model.testcase.value = it
                }
            }

        }
    }

    class Webservices : View() {
        private val model: ProjectModel by inject()
        private val controller: ProjectController by inject()

        override val root = vbox {
            title = "Webservices"
            closeableWhen() { SimpleBooleanProperty(false) }

            buttonbar {
                button("Repair") {
                    enableWhen(model.webservice.isNotNull)
                }.action {
                    controller.startRepair()
                }
            }

            tableview(model.webservices) {
                column(title = "Project", ProjectModel.Webservice::projectName)
                column(title = "Wsdl", ProjectModel.Webservice::wsdlServiceName).remainingWidth()
                smartResize()
                onSelectionChange {
                    model.webservice.value = it
                }
            }
        }

    }


}