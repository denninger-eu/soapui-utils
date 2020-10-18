package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class RestParametersBox(
    private val yaml: MutableList<RestParameterYaml>,
    private val overrides: Boolean,
    private val parent: RestParametersBox?,
    private val store: () -> Unit
) : SuuRestParameters {


    override val allParameters: List<RestParameter>
        get() {
            val allParams = yaml.map { RestParameter(it) }.toMutableList()
            if (parent != null) {
                for (parentParam in parent.allParameters) {
                    if (allParams.firstOrNull { it.name == parentParam.name } == null) {
                        allParams.add(parentParam)
                    }
                }
            }

            return allParams
        }

    override val parameterOverride: List<SuuRestParameter>
        get() = yaml.map { RestParameter(it) }.filter { it.isOverride() }.toList()
    override val parameterOwning: List<SuuRestParameter>
        get() = yaml.map { RestParameter(it) }.filter { it.isOwner() }.toList()

    override fun remove(name: String) {
        yaml.removeIf { it.name == name }
        store()
    }

    override fun addOrUpdate(
        name: String,
        value: String,
        style: SuuRestParameter.Style,
        location: SuuRestParameter.Location
    ) {
        var changed = false
        var existing = yaml.firstOrNull { it.name == name }
        if (existing == null) {
            existing = RestParameterYaml(name = name)
            yaml.add(existing)
            changed = true
        }
        if (changed(existing.value, value)) {
            existing.value = value
            changed = true
        }
        if (existing.style != style) {
            existing.style = style
            changed = true
        }
        if (existing.location != location) {
            existing.location = location
            changed = true
        }

        if (changed(existing.override, overrides, false)) {
            existing.override = overrides
            changed = true
        }
        if (changed) {
            store()
        }
    }

    class RestParameter(private val parameter: RestParameterYaml) : SuuRestParameter {
        override var name: String = parameter.name ?: ""

        override var value: String = parameter.value ?: ""
        override var style: SuuRestParameter.Style = parameter.style ?: SuuRestParameter.Style.QUERY
        override val location: SuuRestParameter.Location = parameter.location ?: SuuRestParameter.Location.UNKNOWN

        override fun isOverride(): Boolean = parameter.override ?: false
    }


    data class RestParameterYaml(
        var name: String? = null,
        var value: String? = null,
        var style: SuuRestParameter.Style? = null,
        var location: SuuRestParameter.Location? = null,
        var override: Boolean? = false
    )

    companion object {
        private fun mapStyle() {

        }
    }
}