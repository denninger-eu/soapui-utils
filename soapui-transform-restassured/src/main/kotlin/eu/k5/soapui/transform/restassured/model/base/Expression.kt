package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter

abstract class Expression {
    abstract fun write(writer: ModelWriter)
}