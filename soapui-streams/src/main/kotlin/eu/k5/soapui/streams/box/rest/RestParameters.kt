package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class RestParameters(
    val params: MutableList<RestParameterYaml>,
    val store: () -> Unit
) : SuuRestParameters {

    override val parameters: List<SuuRestParameter>
        get() = params.map { RestParameter(it) }.toList()

    override fun remove(name: String) {
        params.removeIf { it.name == name }
        store()
    }

    override fun addOrUpdate(name: String, value: String, style: String) {
        var existing = params.firstOrNull { it.name == name }
        if (existing == null) {
            existing = RestParameterYaml(name = name)
            params.add(existing)
        }
        existing.value = value
        existing.style = style

        store()
    }

    class RestParameter(private val parameter: RestParameterYaml) : SuuRestParameter {
        override var name: String? = parameter.name
        override var value: String? = parameter.value
        override var style: String? = parameter.style
    }


    data class RestParameterYaml(
        var name: String? = null,
        var value: String? = null,
        var style: String? = null
    )
}