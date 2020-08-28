package eu.k5.soapui.transform.extensions

import eu.k5.soapui.streams.model.test.SuuPropertyTransfer

fun SuuPropertyTransfer.Transfer.asEntity(): String {
    var entity: String = if (!(this.stepName ?: "").startsWith("#")) {
        "#$stepName"
    } else {
        stepName ?: ""
    }
    return if (!(propertyName ?: "").startsWith("#")
        && !entity.endsWith("#")
    ) {
        "$entity#" + this.propertyName
    } else {
        entity + this.propertyName
    }
}