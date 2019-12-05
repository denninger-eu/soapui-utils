package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class DifferenceMisc(
    private val differences: Differences

) {

    fun handleParameters(refParameters: SuuRestParameters, actual: SuuRestParameters) {
        for (referenceParameter in refParameters.parameterOwning) {
            val actualParameter = actual.byName(referenceParameter.name)
            if (actualParameter != null) {
                differences.addChange("value", referenceParameter.value, actualParameter.value)
                differences.addChange("style", referenceParameter.style, actualParameter.style)
            } else {
                differences.addAdditional(referenceParameter.name)
            }
        }
    }

    fun handleHeaders(
        reference: List<Header>, actual: List<Header>
    ) {

        val found = ArrayList<String>()
        for (refHeader in reference) {
            val actualHeader = actual.firstOrNull { it.key == refHeader.key }
            if (actualHeader != null) {
                differences.addChange("headerValue", refHeader.value, actualHeader.value)
                found.add(refHeader.key)
            } else {
                differences.addAdditional(refHeader.key)
            }
        }
        val missing = ArrayList(actual)
        missing.removeIf { found.contains(it.key) }

        for (missingHeader in missing) {
            differences.addMissing(missingHeader.key)
        }
    }


}