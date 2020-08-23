package eu.k5.soapui.transform.client

import javafx.collections.FXCollections
import javafx.geometry.Orientation
import javafx.scene.control.SelectionMode
import tornadofx.*

class ArtifactsView : View() {

    private val controller: ArtifactsController by inject()

    val artifacts = FXCollections.observableArrayList(Artifact("test", "content"))


    override val root =
        borderpane {
            center {
                splitpane(Orientation.HORIZONTAL) {
                    listview(artifacts) {
                        selectionModel.selectionMode = SelectionMode.SINGLE
                    }
                    textarea {

                    }
                }
            }
            bottom {
                buttonbar {
                    button("Save as zip").action { controller?.saveAsZip() }
                    button("Save in folder ").action { controller?.saveInFolder() }
                }
            }
        }
}