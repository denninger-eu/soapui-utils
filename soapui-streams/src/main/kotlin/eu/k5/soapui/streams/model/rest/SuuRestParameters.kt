package eu.k5.soapui.streams.model.rest

interface SuuRestParameters {

    val allParameters: List<SuuRestParameter>

    val parameters: List<SuuRestParameter>

    fun remove(name: String)

    fun addOrUpdate(name: String, value: String, style: SuuRestParameter.Style)

    fun addOrUpdate(parameter: SuuRestParameter) {
        addOrUpdate(parameter.name!!, parameter.value!!, parameter.style!!)
    }

    fun hasParameter(name: String?): Boolean {
        return allParameters.firstOrNull { it.name == name } != null
    }

    fun byName(name: String): SuuRestParameter? {
        return allParameters.firstOrNull {
            it.name == name
        }
    }

}