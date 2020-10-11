package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.ParameterElement
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class ParametersJaxb(
    private val elements: List<ParameterElement>
) : SuuRestParameters {


    override val allParameters: List<SuuRestParameter>
        get() = TODO("Not yet implemented")

    override fun remove(name: String) {
        TODO("Not yet implemented")
    }

    override fun addOrUpdate(
        name: String,
        value: String,
        style: SuuRestParameter.Style,
        location: SuuRestParameter.Location
    ) {
        TODO("Not yet implemented")
    }

    class ParameterJaxb(
        private val element: ParameterElement,
        override val location: SuuRestParameter.Location
    ) : SuuRestParameter {
        override var name: String
            get() = element.name ?: ""
            set(value) {}
        override var value: String
            get() = element.value ?: ""
            set(value) {}
        override var style: SuuRestParameter.Style
            get() = SuuRestParameter.style(element.style)
            set(value) {}

        override fun isOverride(): Boolean {
            TODO("Not yet implemented")
        }

    }

}