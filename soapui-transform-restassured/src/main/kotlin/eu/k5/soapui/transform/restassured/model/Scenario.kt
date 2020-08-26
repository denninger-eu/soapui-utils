package eu.k5.soapui.transform.restassured.model

class Scenario(
    val name: String
) {

    val fields = ArrayList<Field>()
    val methods = ArrayList<Method>()

}