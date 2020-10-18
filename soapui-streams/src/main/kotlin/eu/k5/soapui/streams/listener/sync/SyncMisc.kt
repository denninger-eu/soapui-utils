package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer

class SyncMisc {

    fun copyHeaders(source: SuuRestRequest, target: SuuRestRequest) {
        val found = ArrayList<String>()
        for (header in source.headers) {
            target.addOrUpdateHeader(header)
            found.add(header.key)
        }
        val missing = ArrayList(target.headers)
        missing.removeIf { found.contains(it.key) }
        for (missingHeader in missing) {
            target.removeHeader(missingHeader.key)
        }
    }

    fun handleParameters(target: SuuRestParameters, source: SuuRestParameters, override: Boolean = false) {

        val targetParams = if (!override) target.parameterOwning else target.parameterOverride
        val sourceParams = if (!override) source.parameterOwning else source.parameterOverride

        val missing = ArrayList<String>()
        for (targetParameter in targetParams) {
            if (!source.hasParameter(targetParameter.name)) {
                missing.add(targetParameter.name)
            }
        }
        missing.forEach { target.remove(it) }

        for (parameter in sourceParams) {
            target.addOrUpdate(parameter)
        }
    }

    fun assignTransferProperties(target: SuuPropertyTransfer, source: SuuPropertyTransfer) {
        target.enabled = source.enabled
        target.source.language = source.source.language
        target.source.expression = source.source.expression
        target.source.stepName = source.source.stepName
        target.source.propertyName = source.source.propertyName

        target.target.language = source.target.language
        target.target.expression = source.target.expression
        target.target.stepName = source.target.stepName
        target.target.propertyName = source.target.propertyName
    }
}