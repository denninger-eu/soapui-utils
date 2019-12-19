package eu.k5.soapui.streams.model.assertion

interface SuuAssertionJsonPathExists : SuuAssertionJsonPath {

    fun expectsExists(): Boolean = "true".equals(expectedContent, ignoreCase = true)
}