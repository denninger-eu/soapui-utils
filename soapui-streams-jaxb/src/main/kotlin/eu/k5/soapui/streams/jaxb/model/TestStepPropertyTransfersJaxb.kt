package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.ConfigPropertyTransfersStepElement
import eu.k5.soapui.streams.jaxb.element.PropertyTransfersElement
import eu.k5.soapui.streams.jaxb.element.TestStepElement
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers

class TestStepPropertyTransfersJaxb(
    element: TestStepElement
) : TestStepJaxb(element), SuuTestStepPropertyTransfers {

    private val config: ConfigPropertyTransfersStepElement = element.config as ConfigPropertyTransfersStepElement

    override val transfers: List<SuuPropertyTransfer>
        get() = config.transfers?.map { PropertyTransferJaxb(it) } ?: ArrayList()

    override fun addTransfer(name: String): SuuPropertyTransfer {
        TODO("Not yet implemented")
    }

    class PropertyTransferJaxb(
        private val element: PropertyTransfersElement
    ) : SuuPropertyTransfer {
        override var name: String
            get() = element.name ?: ""
            set(value) {
                element.name = value
            }
        override var enabled: Boolean
            get() = element.disabled?.not() ?: true
            set(value) {
                element.disabled = !value
            }

        override val source: SuuPropertyTransfer.Transfer
            get() = object : SuuPropertyTransfer.Transfer {
                override var expression: String?
                    get() = element.sourcePath
                    set(value) {
                        element.sourcePath = value
                    }
                override var propertyName: String?
                    get() = element.sourceType
                    set(value) {
                        element.sourceType = value
                    }
                override var stepName: String?
                    get() = element.sourceStep
                    set(value) {
                        element.sourceStep = value
                    }
                override var language: SuuPropertyTransfer.Language
                    get() = mapLanguage(element.sourceTransferType)
                    set(value) {}
            }

        override val target: SuuPropertyTransfer.Transfer
            get() = object : SuuPropertyTransfer.Transfer {
                override var expression: String?
                    get() = element.targetPath
                    set(value) {
                        element.targetPath = value
                    }
                override var propertyName: String?
                    get() = element.targetType
                    set(value) {
                        element.targetType = value
                    }
                override var stepName: String?
                    get() = element.targetStep
                    set(value) {
                        element.targetStep = value
                    }
                override var language: SuuPropertyTransfer.Language
                    get() = mapLanguage(element.targetTransferType)
                    set(value) {}
            }

    }

    companion object {
        fun mapLanguage(pathLanguage: String?): SuuPropertyTransfer.Language {
            return when (pathLanguage) {
                "XQUERY" -> SuuPropertyTransfer.Language.XQUERY
                "JSONPATH" -> SuuPropertyTransfer.Language.JSONPATH
                else -> SuuPropertyTransfer.Language.XPATH
            }
        }

        fun mapLanguage(language: SuuPropertyTransfer.Language): String {
            return when (language) {
                SuuPropertyTransfer.Language.XQUERY -> "XQUERY"
                SuuPropertyTransfer.Language.JSONPATH -> "JSONPATH"
                else -> "XPATH"
            }
        }

    }
}