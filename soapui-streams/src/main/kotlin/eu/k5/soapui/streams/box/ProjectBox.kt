package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.net.FileNameMap
import java.nio.file.Path

class ProjectBox(
    private val box: Box
) : SuProject {

    private val project: ProjectYaml by lazy { box.load(ProjectYaml::class.java) }

    override var name: String = project.name!!

    override var description: String? = project.description

    override val restServices: MutableList<RestServiceBox> by lazy {
        box.findSubFolderBox { it.fileName.toString() == RestServiceBox.FILE_NAME }.map { RestServiceBox(it) }
            .toMutableList()
    }


    override fun createRestService(name: String): SuuRestService {
        val newRestService = RestServiceBox.create(box, name)
        restServices.add(newRestService)
        return newRestService
    }

    class ProjectYaml {
        var name: String? = null
        var description: String? = null
    }

    companion object {

        const val FILE_NAME = "project.box.yaml"

        fun create(path: Path, name: String): ProjectBox {
            val project = ProjectYaml()
            project.name = name
            val box = Box(path).write(ProjectYaml::class.java, project)
            return ProjectBox(box)
        }

        fun load(path: Path): ProjectBox {
            return ProjectBox(Box(path.resolve(FILE_NAME)))
        }
    }

}