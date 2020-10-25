package eu.k5.soapui.transform.client

import javafx.geometry.Orientation
import javafx.scene.control.SelectionMode
import tornadofx.*

class ArtifactsView(

) : View() {
    override val scope = super.scope as ArtifactsScope

    private val controller: ArtifactsController by inject()
    private val model = scope.model

    override val root =
        borderpane {
            center {
                splitpane(Orientation.HORIZONTAL) {
                    tableview(model.artifacts) {
                        column("Name", Artifact::nameProperty).remainingWidth()
                        column("Type", Artifact::typeProperty)
                        smartResize()

                        model.selected.rebindOnChange(this) { selectedArtifact ->
                            item = selectedArtifact ?: Artifact("", "")
                        }
                    }
                    borderpane {
                        center {
                            textarea(model.selected.content)
                        }
                    }
                }
            }
            bottom {
                buttonbar {
                    button("Save as zip").action {
                        controller.saveAsZip()
                    }
                    button("Save in folder ").action {
                        val initDirectory = controller.getOpenSaveInFolderFolder()

                        var directory = chooseDirectory(initialDirectory = initDirectory)
                        if (directory != null) {
                            controller.saveInDirectory(directory)
                        }
                    }
                }
            }
        }
}