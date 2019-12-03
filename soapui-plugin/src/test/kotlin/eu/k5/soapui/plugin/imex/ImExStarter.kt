package eu.k5.soapui.plugin.imex

import com.eviware.soapui.impl.wsdl.WsdlProject
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
    model.restService = null
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

fun loadProject(name: String): WsdlProject {
    val path = Paths.get(
        "..",
        "soapui-streams-direct",
        "src",
        "test",
        "resources",
        "testcases",
        name
    )
    return Files.newInputStream(path).use { WsdlProject(it, null) }
}

fun loadProject(): WsdlProject = loadProject("RestServiceComplete-soapui-project.xml")
