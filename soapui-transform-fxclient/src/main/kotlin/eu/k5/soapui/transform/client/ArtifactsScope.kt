package eu.k5.soapui.transform.client

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Scope

class ArtifactsScope(
    val artifacts: ObservableList<Artifact> = FXCollections.observableArrayList()

) : Scope()