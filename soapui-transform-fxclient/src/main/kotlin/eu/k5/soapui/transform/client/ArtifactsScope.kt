package eu.k5.soapui.transform.client

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Scope

class ArtifactsScope() : Scope() {
    val model = ArtifactsModel()
}