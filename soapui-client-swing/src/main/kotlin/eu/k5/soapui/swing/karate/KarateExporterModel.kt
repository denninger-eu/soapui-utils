package eu.k5.soapui.swing.karate

import eu.k5.soapui.swing.Observable
import javax.swing.DefaultListModel

class KarateExporterModel {

    val artifacts = DefaultListModel<Artifact>()

    var current: Observable<Artifact?> = Observable(null)

    class Artifact(
        val name: String,
        val content: String


    ) {
        override fun toString(): String {
            return name
        }
    }


    fun addArtifact(name: String, content: String) {
        artifacts.add(artifacts.size(), Artifact(name, content))
    }

}