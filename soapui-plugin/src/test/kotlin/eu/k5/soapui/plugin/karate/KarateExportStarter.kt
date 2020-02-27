package eu.k5.soapui.plugin.karate

import javax.swing.SwingUtilities


fun main(args: Array<String>) {

    val model = KarateExporterModel()

    model.artifacts.add(0, KarateExporterModel.Artifact("Test", "content"))
    model.artifacts.add(1, KarateExporterModel.Artifact("Test1", "content1"))
    model.artifacts.add(2, KarateExporterModel.Artifact("Test2", "content2"))

    val view = KarateExporterView(model)
    SwingUtilities.invokeLater { view.display() }

}