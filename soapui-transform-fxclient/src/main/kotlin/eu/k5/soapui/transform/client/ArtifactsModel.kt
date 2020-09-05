package eu.k5.soapui.transform.client

import javafx.beans.property.ReadOnlyStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Component
import tornadofx.ItemViewModel
import tornadofx.ScopedInstance

class ArtifactsModel : Component(), ScopedInstance {
    val artifacts: ObservableList<Artifact> = FXCollections.observableArrayList()
    val selected = ArtifactModel(Artifact())


    class ArtifactModel(artifact: Artifact) : ItemViewModel<Artifact>() {


        val name = bind(Artifact::nameProperty) as ReadOnlyStringProperty
        val content = bind(Artifact::contentProperty) as ReadOnlyStringProperty

    }
}