package eu.k5.soapui.executor.runner

import eu.k5.soapui.executor.Runner
import eu.k5.soapui.executor.RunnerContext
import eu.k5.soapui.streams.model.test.SuuTestStepProperties

class PropertiesRunner(
    private val step: SuuTestStepProperties
) : Runner {

    override fun initContext(context: RunnerContext) {
        val properties = step.properties

        for (property in properties.properties) {
            if (property.value != null) {
                context.addProperty(property.name, property.value!!)
            }
        }
    }

    override fun run(context: RunnerContext) {
        context.reportStep(step.name)

        val properties = step.properties

        for (property in properties.properties) {
            if (property.value != null) {
                context.addProperty(property.name, property.value!!)
            }
        }
    }

}