package eu.k5.soapui.streams.model.rest

interface SuuRestParameters {

    //val allParameters: List<SuuRestParameter>

    val allParameters: List<SuuRestParameter>

    val parameterOwning: List<SuuRestParameter> get() = allParameters.filter { it.isOwner() }

    val parameterOverride: List<SuuRestParameter> get() = allParameters.filter { it.isOverride() }

    fun remove(name: String)

    fun addOrUpdate(name: String, value: String, style: SuuRestParameter.Style, location: SuuRestParameter.Location)

    fun addOrUpdate(parameter: SuuRestParameter) =
        addOrUpdate(
            parameter.name, parameter.value, parameter.style, parameter.location
        )

    fun hasParameter(name: String?): Boolean = allParameters.firstOrNull { it.name == name } != null

    fun byName(name: String): SuuRestParameter? = allParameters.firstOrNull { it.name == name }

}