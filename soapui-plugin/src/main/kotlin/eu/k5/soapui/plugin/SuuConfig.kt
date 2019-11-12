package eu.k5.soapui.plugin

import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.xml.bind.JAXBContext
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
class SuuConfig {

    @XmlElement(name = "project")
    var projects: MutableList<ProjectConfig>? = ArrayList<ProjectConfig>()

    @XmlTransient
    var origin: Path? = null

    fun projectConfigByName(name: String): ProjectConfig? {
        return projects?.firstOrNull { it.name == name }
    }

    fun createIfAbsent(name: String): ProjectConfig {
        var project = projectConfigByName(name)
        if (project == null) {
            project = ProjectConfig()
            project.name = name
            projects?.add(project)
        }
        return project
    }


    @XmlAccessorType(XmlAccessType.NONE)
    class ProjectConfig {

        @XmlAttribute
        var name: String? = null

        @XmlAttribute
        var base: String? = null

    }

    fun write() {
        write(this, origin!!)
    }

    companion object {
        private val CONTEXT = JAXBContext.newInstance(SuuConfig::class.java)

        private fun resolveDefault(): Path {
            val userHome = System.getProperty("user.home")
            val path = Paths.get(userHome).resolve(".soapuios").resolve("soapui-utils-config.xml")

            return path
        }

        fun loadDefault(): SuuConfig {
            return load(resolveDefault())
        }

        fun writeDefault() {
            val path = resolveDefault()
            if (!Files.exists(path.parent)) {
                Files.createDirectories(path)
            }

        }

        fun load(path: Path): SuuConfig {
            if (!Files.exists(path)) {
                val config = SuuConfig()
                config.origin = path
                return config
            }
            val instance = CONTEXT.createUnmarshaller().unmarshal(path.toFile())
            if (instance is SuuConfig) {
                instance.origin = path
                return instance
            }
            throw IllegalArgumentException("File does not contain SuuConfig")
        }

        fun write(config: SuuConfig, path: Path) {
            CONTEXT.createMarshaller().marshal(config, path.toFile())
        }
    }
}