package eu.k5.soapui.streams.model.rest

interface SuuRestParameters {

    //val allParameters: List<SuuRestParameter>

    val allParameters: List<SuuRestParameter>

    val parameterOwning: List<SuuRestParameter> get() = allParameters.filter { it.isOwner() }

    val parameterOverride: List<SuuRestParameter> get() = allParameters.filter { !it.isOwner() }

    fun remove(name: String)

    fun addOrUpdate(name: String, value: String, style: SuuRestParameter.Style)

    fun addOrUpdate(parameter: SuuRestParameter) = addOrUpdate(parameter.name!!, parameter.value!!, parameter.style!!)

    fun hasParameter(name: String?): Boolean = allParameters.firstOrNull { it.name == name } != null

    fun byName(name: String): SuuRestParameter? = allParameters.firstOrNull { it.name == name }

}