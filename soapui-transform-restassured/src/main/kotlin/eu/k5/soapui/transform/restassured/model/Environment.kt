package eu.k5.soapui.transform.restassured.model

class Environment {

    fun context() = "SoapuiContext"

    fun contextFqn() = "eu.k5.dread.soapui.SoapuiContext"

    fun propertyHolder() = "PropertyHolder"

    fun propertyHolderFqn() = contextFqn() + "." + propertyHolder()


}