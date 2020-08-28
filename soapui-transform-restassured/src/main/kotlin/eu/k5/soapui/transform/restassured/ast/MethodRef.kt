package eu.k5.soapui.transform.restassured.ast

interface MethodRef {
    val name: String

    companion object {
        fun withName(name: String) = object : MethodRef {
            override val name: String
                get() = name
        }
    }
}