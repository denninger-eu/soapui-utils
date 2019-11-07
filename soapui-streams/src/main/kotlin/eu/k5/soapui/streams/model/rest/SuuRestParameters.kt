package eu.k5.soapui.streams.model.rest

interface SuuRestParameters {

    val parameters: List<SuuRestParameter>

    fun remove(name: String)

    fun addOrUpdate(name: String, value: String, style: String)

    fun addOrUpdate(parameter: SuuRestParameter) {
        addOrUpdate(parameter.name!!, parameter.value!!, parameter.style!!)
    }

    fun hasParameter(name: String?): Boolean {
        return parameters.firstOrNull { it.name == name } != null
    }

}