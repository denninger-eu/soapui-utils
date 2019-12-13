package eu.k5.soapui.executor.runner

import com.jayway.jsonpath.JsonPath
import eu.k5.soapui.executor.Runner
import eu.k5.soapui.executor.RunnerContext
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import java.lang.UnsupportedOperationException

class PropertyTransferRunner(
    private val step: SuuTestStepPropertyTransfers

) : Runner {

    override fun initContext(context: RunnerContext) {
    }

    override fun run(context: RunnerContext) {
        context.reportStep(step.name)

        for (transfer in step.transfers) {
            val property = getSource(context, transfer.source)
            if (property != null) {
                updateTarget(context, transfer.target, property)
                println(property)
            }
        }

    }

    private fun updateTarget(
        context: RunnerContext,
        target: SuuPropertyTransfer.Transfer,
        value: String
    ) {
        if (target.expression.isNullOrEmpty()) {
            context.addProperty(target.stepName + target.propertyName, value)
            return
        }
        /*
        when {
            target.language == SuuPropertyTransfer.Language.JSONPATH -> updateJsonPath()
        }
*/
        TODO("implement")
    }

    private fun getSource(
        context: RunnerContext,
        source: SuuPropertyTransfer.Transfer
    ): String? {
        val property = context.getProperty(source.stepName + "." + source.propertyName) ?: return null
        if (source.expression.isNullOrEmpty() || property.isNullOrEmpty()) {
            return property
        }
        return when {
            source.language == SuuPropertyTransfer.Language.JSONPATH -> extractJsonPath(property, source.expression!!)
            source.language == SuuPropertyTransfer.Language.XPATH -> throw UnsupportedOperationException("xpath")
            source.language == SuuPropertyTransfer.Language.XQUERY -> throw UnsupportedOperationException("xquery")
            else -> property
        }
    }

    private fun extractJsonPath(json: String, expression: String): String? {
        return JsonPath.read<String>(json, expression)
    }

    private fun updateJsonPath(json: String, expression: String): String {
        return json
    }

}