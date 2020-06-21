package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.PropertiesElement
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.SuuProperty
import java.lang.UnsupportedOperationException

class PropertiesJaxb(
    private val element: PropertiesElement
) : SuuProperties {

    override val properties: List<SuuProperty>
        get() = element.properties?.map { PropertyJaxb(it) }.orEmpty()

    override fun remove(name: String) = throw UnsupportedOperationException()

    override fun addOrUpdate(name: String, value: String?) = throw UnsupportedOperationException()


    class PropertyJaxb(
        private val element: PropertiesElement.PropertyElement
    ) : SuuProperty {

        override val name: String
            get() = element.name ?: ""
        override val value: String?
            get() = element.value

    }
}