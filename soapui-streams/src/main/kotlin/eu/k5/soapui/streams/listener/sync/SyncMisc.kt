package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer

class SyncMisc {

    fun copyHeaders(source: SuuRestRequest, target: SuuRestRequest) {
        for (header in source.headers) {
            target.addOrUpdateHeader(header)
        }
    }

    fun handleParameters(target: SuuRestParameters, source: SuuRestParameters) {

        val missing = ArrayList<String>()
        for (targetParameter in target.allParameters) {
            if (!source.hasParameter(targetParameter.name)) {
                missing.add(targetParameter.name!!)
            }
        }
        missing.forEach { target.remove(it) }

        for (parameter in source.allParameters) {
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