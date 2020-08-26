package eu.k5.soapui.transform.restassured.model

import eu.k5.soapui.transform.ModelWriter

abstract class MethodBody {

    abstract fun write(writer: ModelWriter)

}