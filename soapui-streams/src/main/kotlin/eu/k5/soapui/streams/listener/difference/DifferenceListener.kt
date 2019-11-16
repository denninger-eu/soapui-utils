package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener

class DifferenceListener(
    private val referenceProject: SuProject

) : SuListener {
    val differences = Differences()

    override fun enterProject(env: Environment, project: SuProject) {
        differences.pushProject("project")
        if (project.name != referenceProject.name) {
            differences.addChange("name")
        }
        if (project.description != referenceProject.description) {
            differences.addChange("description")
        }
        handleProperties(differences, referenceProject.properties, project.properties)
    }

    override fun exitProject(suuProject: SuProject) {

    }


    override fun createResourceListener(): SuuRestServiceListener {
        return RestServiceDifferenceListener(referenceProject, differences)
    }

    override fun createTestSuiteListener(): SuuTestSuiteListener {
        return DifferenceTestSuiteListener(differences, referenceProject)
    }

    companion object {

        fun handleProperties(differences: Differences, reference: SuuProperties, target: SuuProperties) {
            for (targetProperty in target.properties) {
                if (!reference.hasProperty(targetProperty.name)) {
                    differences.addAdditional(targetProperty.name!!)
                }
            }
            for (referenceProperty in reference.properties) {
                val targetProperty = target.byName(referenceProperty.name)
                if (targetProperty != null) {
                    differences.addChange(referenceProperty.name, referenceProperty.value, targetProperty.value)
                } else {
                    differences.addMissing(referenceProperty.name)
                }
            }

        }
    }

}


