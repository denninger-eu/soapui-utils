package eu.k5.soapui.transform.restassured.model

class RaRequestStep(
    val name: String,
    val method: String
) {

    val parameters = HashMap<String, MutableList<String>>()
    val headers = HashMap<String, MutableList<String>>()
    var body: String = ""


    fun addParameter(name: String, value: String) = parameters.computeIfAbsent(name) { ArrayList() }.add(value)
    fun addHeader(name: String, value: String) = headers.computeIfAbsent(name) { ArrayList() }.add(value)
}