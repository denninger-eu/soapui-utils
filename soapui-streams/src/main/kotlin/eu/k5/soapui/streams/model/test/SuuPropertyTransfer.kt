package eu.k5.soapui.streams.model.test

interface SuuPropertyTransfer {

    var name: String

    var enabled: Boolean

    val source: PropertyReference
    val target: PropertyReference
/*
    var sourcePath: String?
    var sourceType: String?
    var sourceLanguage: Language

    var targetPath: String?
    var targetType: String?
    var targetLanguage: Language
*/


    interface PropertyReference {
        var expression: String?

        var propertyName: String?

        var stepName: String?

        var language: Language


    }

    enum class Language {
        XPATH, XQUERY, JSONPATH
    }
}