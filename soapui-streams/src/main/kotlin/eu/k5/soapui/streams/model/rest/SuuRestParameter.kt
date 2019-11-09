package eu.k5.soapui.streams.model.rest

import com.sun.org.apache.xpath.internal.operations.Bool

interface SuuRestParameter {
    var name: String
    var value: String
    var style: Style
    val location: Location

    fun isOwner(): Boolean {
        return location == Location.RESOURCE || location == Location.METHOD
    }

    enum class Location {
        UNKNOWN, RESOURCE, RESOURCE_OVERRIDE, METHOD, METHOD_OVERRIDE
    }

    enum class Style {
        HEADER, QUERY, TEMPLATE
    }

}