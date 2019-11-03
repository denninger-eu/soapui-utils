package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.nio.file.Files

class ProjectBox(
    private val box: Box
) : SuProject {

    private val project: ProjectYaml
        get() = box.load(ProjectYaml::class.java)

    override var name: String = project.name!!

    override var description: String? = project.description

    override val restServices: List<SuuRestService>
        get() = box.findFolderBox { it.fileName.toString() == "restservice.box.yaml" }.map { RestServiceBox(it) }

    override fun createRestService(name: String): SuuRestService {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ProjectYaml {
        var name: String? = null
        var description: String? = null
    }

}