package eu.k5.soapui.plugin.imex

import com.eviware.soapui.impl.wsdl.WsdlProject
import eu.k5.soapui.plugin.SuuConfig
import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.direct.DirectLoader
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.model.SuProject
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import java.util.concurrent.Executors
import javax.swing.SwingUtilities


fun main(args: Array<String>) {


    println(Paths.get(".").toAbsolutePath().toString())
    val project = loadProject()


    val wsdlProject2 = loadProject("TestSuiteProjectChanged-soapui-project.xml")

    val model = ImexModel(project, config = getConfig())
    model.restService = null
    model.folder.update( model.config.origin!!.parent.toString())


    val view = ImexView(model)

    model.target.update(ProjectDirect(wsdlProject2))

    // executor.submit()
    //Differ(model, project, wsdlProject2).run()

    SwingUtilities.invokeLater { view.display() }

}


class Differ(
    val model: ImexModel,
    val project: WsdlProject,
    val project2: WsdlProject
) : Runnable {
    override fun run() {
        val diff = Suu.diff(ProjectDirect(project), ProjectDirect(project2))
        model.differences.update(diff)
    }
}


private fun getConfig(): SuuConfig {
    val configs = Paths.get("target", "configs", Instant.now().toString().replace(":", "_"))
    if (!Files.exists(configs)) {
        Files.createDirectories(configs)
    }
    return SuuConfig.load(configs.resolve("config.xml"))
}


private fun searchRoot(): Path {
    var path: Path? = Paths.get(".").toAbsolutePath()
    println("Current working directory: $path")
    for (x in 0..10) {
        if (Files.exists(path?.resolve("soapui-utils"))) {
            return path!!
        }
        path = path?.parent
        if (path == null) {
            throw IllegalStateException("unable to find project root")
        }
    }
    throw IllegalStateException("unable to find project root, max depth")
}

fun loadProject(name: String): WsdlProject {
    val root = searchRoot()

    val path =
        root.resolve("soapui-utils").resolve("soapui-streams-direct").resolve("src").resolve("test")
            .resolve("resources").resolve("testcases")
            .resolve(name)
    return Files.newInputStream(path).use { WsdlProject(it, null) }
}

fun loadProject(): WsdlProject = loadProject("RestServiceComplete-soapui-project.xml")
