package eu.k5.soapui.streams.model

interface SuuProperties {

    val properties: List<SuuProperty>

    fun remove(name: String)

    fun addOrUpdate(property: SuuProperty) = addOrUpdate(property.name, property.value)

    fun addOrUpdate(name: String, value: String?)
    fun hasProperty(name: String): Boolean = properties.firstOrNull { it.name == name } != null

    fun byName(name: String): SuuProperty? = properties.firstOrNull { it.name == name }


}
