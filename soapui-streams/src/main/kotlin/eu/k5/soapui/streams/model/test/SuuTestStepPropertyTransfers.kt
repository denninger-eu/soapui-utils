package eu.k5.soapui.streams.model.test

interface SuuTestStepPropertyTransfers : SuuTestStep {

    val transfers: List<SuuPropertyTransfer>

    fun getTransfer(name: String) = transfers.firstOrNull { it.name == name }

    fun addTransfer(name: String): SuuPropertyTransfer

}