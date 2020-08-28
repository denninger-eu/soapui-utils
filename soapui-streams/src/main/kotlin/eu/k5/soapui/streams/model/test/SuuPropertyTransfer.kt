package eu.k5.soapui.streams.model.test

interface SuuPropertyTransfer {

    var name: String

    var enabled: Boolean

    val source: Transfer

    val target: Transfer

    interface Transfer {
        var expression: String?

        var propertyName: String?

        var stepName: String?

        var language: Language

    }

    enum class Language {
        XPATH, XQUERY, JSONPATH
    }
}