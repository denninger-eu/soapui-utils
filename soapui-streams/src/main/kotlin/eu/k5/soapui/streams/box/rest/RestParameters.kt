package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class RestParameters(
    private val params: MutableList<RestParameterYaml>,
    private val store: () -> Unit
) : SuuRestParameters {

    override val allParameters: List<SuuRestParameter>
        get() = params.map { RestParameter(it) }.toList()

    override val parameters: List<SuuRestParameter>
        get() = allParameters


    override fun remove(name: String) {
        params.removeIf { it.name == name }
        store()
    }

    override fun addOrUpdate(name: String, value: String, style: SuuRestParameter.Style) {
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
        override var name: String = parameter.name ?: ""
        override var value: String = parameter.value ?: ""
        override var style: SuuRestParameter.Style = parameter.style ?: SuuRestParameter.Style.QUERY
        override val location: SuuRestParameter.Location = parameter.location ?: SuuRestParameter.Location.RESOURCE
    }


    data class RestParameterYaml(
        var name: String? = null,
        var value: String? = null,
        var style: SuuRestParameter.Style? = null,
        var location: SuuRestParameter.Location? = null
    )

    companion object {
        private fun mapStyle() {

        }
    }
}