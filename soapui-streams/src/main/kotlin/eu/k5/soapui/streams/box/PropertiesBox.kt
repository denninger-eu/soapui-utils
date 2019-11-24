package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.SuuProperty

class PropertiesBox(
    private val base: MutableList<PropertyYaml>,
    private val store: () -> Unit

) : SuuProperties {
    override val properties: List<SuuProperty>
        get() = base.map { Property(it) }


    override fun remove(name: String) {
        base.removeIf { it.name == name }
        store()
    }

    override fun addOrUpdate(name: String, value: String?) {
        var existing = base.firstOrNull { it.name == name }
        if (existing == null) {
            existing = PropertyYaml(name = name)
            base.add(existing)
        }
        existing.value = value
        store()
    }

    class Property(
        private val property: PropertyYaml
    ) : SuuProperty {
        override var name: String = property.name ?: ""
        override var value: String? = property.value
    }


    data class PropertyYaml(
        var name: String? = null,
        var value: String? = null
    )

}