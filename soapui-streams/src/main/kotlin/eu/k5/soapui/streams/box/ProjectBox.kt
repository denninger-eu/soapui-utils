package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.box.test.TestSuiteBox
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestSuite
import java.nio.file.Files
import java.nio.file.Path

class ProjectBox(
    private val box: Box
) : SuProject {

    private val project: ProjectYaml by lazy { box.load(ProjectYaml::class.java) }

    fun fileName(): String {
        return box.toString()
    }

    override var name: String = project.name!!

    override var description: String?
        get() = project.description
        set(value) {
            if (project.description != value) {
                project.description = value
                store()
            }
        }
    override val properties: SuuProperties
            by lazy { PropertiesBox(project.properties!!) { store() } }


    override val restServices: MutableList<RestServiceBox> by lazy {
        box.findSubFolderBox { it.fileName.toString() == RestServiceBox.FILE_NAME }.map { RestServiceBox(it) }
            .toMutableList()
    }

    override fun createRestService(name: String): SuuRestService {
        val newRestService = RestServiceBox.create(box, name)
        restServices.add(newRestService)
        return newRestService
    }

    override val testSuites: MutableList<TestSuiteBox> by lazy {
        box.findSubFolderBox { it.fileName.toString() == TestSuiteBox.FILE_NAME }.map { TestSuiteBox(it) }
            .toMutableList()
    }

    override fun createTestSuite(name: String): SuuTestSuite {
        val init = testSuites
        val newTestSuite = TestSuiteBox.create(box, name)
        init.add(newTestSuite)
        return newTestSuite
    }

    private fun store() {
        box.write(ProjectYaml::class.java, project)

    }

    class ProjectYaml {
        var name: String? = null
        var description: String? = null
        var properties: MutableList<PropertiesBox.PropertyYaml>? = ArrayList()
    }

    companion object {

        const val FILE_NAME = "project.box.yaml"

        fun create(path: Path, name: String): ProjectBox {
            Files.createFile(path)
            val project = ProjectYaml()
            project.name = name
            val box = BoxImpl(path).write(ProjectYaml::class.java, project)
            return ProjectBox(box)
        }

        fun load(path: Path): ProjectBox {
            return ProjectBox(BoxImpl(path.resolve(FILE_NAME)))
        }

        fun clone(source: Path, target: Path) {

        }
    }

}