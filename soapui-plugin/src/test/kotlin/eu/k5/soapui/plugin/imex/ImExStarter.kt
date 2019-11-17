package eu.k5.soapui.plugin.imex

import eu.k5.soapui.plugin.SuuConfig
import eu.k5.soapui.streams.direct.DirectLoader
import eu.k5.soapui.streams.model.SuProject
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import javax.swing.SwingUtilities


fun main(args: Array<String>) {

    val project = loadProject()


    val model = ImexModel(project, config = getConfig())
    model.restService = project.restServices[0]
    model.folder = model.config.origin!!.parent.toString()
    val view = ImexView(model)

    view.display()

    SwingUtilities.invokeLater { view.display() }
}


private fun getConfig(): SuuConfig {
    val configs = Paths.get("target", "configs", Instant.now().toString().replace(":", "_"))
    if (!Files.exists(configs)) {
        Files.createDirectories(configs)
    }
    return SuuConfig.load(configs.resolve("config.xml"))
}

private fun loadProject(): SuProject {
    val path = Paths.get(
        "..",
        "soapui-streams-direct",
        "src",
        "test",
        "resources",
        "testcases",
        "RestServiceComplete-soapui-project.xml"
    )
    return Files.newInputStream(path).use { DirectLoader().direct(it) }
}
