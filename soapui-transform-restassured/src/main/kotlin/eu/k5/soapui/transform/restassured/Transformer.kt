package eu.k5.soapui.transform.restassured

interface Transformer<T> {
    fun transform(step: T)
}