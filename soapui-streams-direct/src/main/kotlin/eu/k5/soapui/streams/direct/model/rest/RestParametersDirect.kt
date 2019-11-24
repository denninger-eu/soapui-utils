package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.actions.support.NewRestResourceActionBase
import com.eviware.soapui.impl.rest.support.RestParamsPropertyHolder
import com.eviware.soapui.impl.rest.support.RestRequestParamsPropertyHolder
import com.eviware.soapui.impl.rest.support.XmlBeansRestParamsTestPropertyHolder
import com.eviware.soapui.model.testsuite.TestProperty
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class RestParametersDirect(
    private val params: RestParamsPropertyHolder,
    private val owner: Owner
) : SuuRestParameters {

    override val allParameters: List<SuuRestParameter>
        get() = params.map { asParameter(it) }.toList()

    override val parameterOverride: List<SuuRestParameter>
        get() = params.map { asParameter(it) }.filter { !it.isOwner() && it.isOverride() }.toList()


    override fun remove(name: String) {

    }

    override fun addOrUpdate(
        name: String, value: String, style: SuuRestParameter.Style, location: SuuRestParameter.Location
    ) {
        val property = params.addProperty(name)
        property.value = value
        property.style = mapStyle(style)
        params.addParameter(property)
    }

    fun asParameter(it: Map.Entry<String, TestProperty>): SuuRestParameter {
        val value = it.value
        return when (value) {
            is XmlBeansRestParamsTestPropertyHolder.XmlBeansRestParamProperty
            -> RestParameterDirectForResource(value, owner)
            is RestRequestParamsPropertyHolder.InternalRestParamProperty
            -> RestParameterDirectForMethodAndRequest(
                value,
                owner
            )
            else
            -> RestParameterDirectGeneric(value, owner)
        }
    }

    class RestParameterDirectGeneric(
        private val param: TestProperty,
        private val owner: Owner
    ) : SuuRestParameter {
        override var name: String = param.name ?: ""

        override var value: String = param.value
        override var style: SuuRestParameter.Style = SuuRestParameter.Style.QUERY
        override val location: SuuRestParameter.Location = SuuRestParameter.Location.UNKNOWN
        override fun isOverride(): Boolean = false
    }

    class RestParameterDirectForMethodAndRequest(
        private val paramProperty: RestRequestParamsPropertyHolder.InternalRestParamProperty,
        private val owner: Owner
    ) : SuuRestParameter {

        override var name: String = paramProperty.name ?: ""
        override var value: String = paramProperty.value
        override var style: SuuRestParameter.Style =
            mapStyle(paramProperty.style)
        override val location: SuuRestParameter.Location =
            mapLocation(
                paramProperty,
                owner,
                paramProperty.paramLocation
            )

        override fun isOverride(): Boolean = paramProperty.defaultValue != paramProperty.value


    }

    class RestParameterDirectForResource(
        private val paramProperty: XmlBeansRestParamsTestPropertyHolder.XmlBeansRestParamProperty,
        private val owner: Owner
    ) : SuuRestParameter {
        override var name: String = paramProperty.name ?: ""

        override var value: String = paramProperty.value
        override var style: SuuRestParameter.Style =
            mapStyle(paramProperty.style)
        override val location: SuuRestParameter.Location =
            mapLocation(
                paramProperty,
                owner,
                paramProperty.paramLocation
            )

        override fun isOverride(): Boolean = false
    }

    companion object {
        private fun mapLocation(
            param: TestProperty,
            owner: Owner,
            location: NewRestResourceActionBase.ParamLocation

        ): SuuRestParameter.Location {
            if (location == NewRestResourceActionBase.ParamLocation.METHOD) {
                if (owner == Owner.METHOD) {
                    return SuuRestParameter.Location.METHOD
                } else if (owner == Owner.REQUEST) {
                    return SuuRestParameter.Location.METHOD_OVERRIDE
                }
            } else if (location == NewRestResourceActionBase.ParamLocation.RESOURCE) {
                if (owner == Owner.RESOURCE) {
                    return SuuRestParameter.Location.RESOURCE
                } else if (owner == Owner.METHOD) {
                    return SuuRestParameter.Location.RESOURCE_OVERRIDE
                } else if (owner == Owner.REQUEST) {
                    return SuuRestParameter.Location.RESOURCE_OVERRIDE
                }
            }
            return SuuRestParameter.Location.UNKNOWN
        }

        private fun mapStyle(style: RestParamsPropertyHolder.ParameterStyle?): SuuRestParameter.Style {
            if (style == null) {
                return SuuRestParameter.Style.QUERY
            }
            return SuuRestParameter.Style.valueOf(style.name)
        }

        private fun mapStyle(style: SuuRestParameter.Style?): RestParamsPropertyHolder.ParameterStyle {
            if (style == null) {
                return RestParamsPropertyHolder.ParameterStyle.QUERY
            }
            return RestParamsPropertyHolder.ParameterStyle.valueOf(style.name)
        }

    }

    enum class Owner {
        RESOURCE, METHOD, REQUEST
    }
}