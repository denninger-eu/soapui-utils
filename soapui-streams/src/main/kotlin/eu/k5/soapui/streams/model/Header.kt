package eu.k5.soapui.streams.model

class Header(
    val key: String,
    val value: List<String>
) {
    constructor(key: String, value: String) : this(key, listOf(value))
}