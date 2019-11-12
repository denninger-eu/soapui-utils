package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.wsdl.teststeps.PathLanguage
import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfer
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import javax.xml.xpath.XPath

class PropertyTransferDirect(
    private val propertyTransfer: PropertyTransfer
) : SuuPropertyTransfer {

    override var name: String
        get() = propertyTransfer.name
        set(value) {
            propertyTransfer.name = value
        }
    override var enabled: Boolean
        get() = !propertyTransfer.isDisabled
        set(value) {
            propertyTransfer.isDisabled = !value
        }

    override val source = SourceReference(propertyTransfer)

    override val target = TargetReference(propertyTransfer)


    class SourceReference(
        private val propertyTransfer: PropertyTransfer
    ) : SuuPropertyTransfer.PropertyReference {
        override var propertyName: String?
            get() = propertyTransfer.sourcePropertyName
            set(value) {
                propertyTransfer.sourcePropertyName = value
            }
        override var stepName: String?
            get() = propertyTransfer.sourceStepName
            set(value) {
                propertyTransfer.sourceStepName = value
            }
        override var expression: String?
            get() = propertyTransfer.sourcePath
            set(value) {
                propertyTransfer.sourcePath = value
            }
        override var language: SuuPropertyTransfer.Language
            get() = mapLanguage(propertyTransfer.sourcePathLanguage)
            set(value) {
                propertyTransfer.sourcePathLanguage = mapLanguage(value)
            }

    }

    class TargetReference(
        private val propertyTransfer: PropertyTransfer
    ) : SuuPropertyTransfer.PropertyReference {
        override var expression: String?
            get() = propertyTransfer.targetPath
            set(value) {
                propertyTransfer.targetPath = value
            }
        override var language: SuuPropertyTransfer.Language
            get() = mapLanguage(propertyTransfer.targetPathLanguage)
            set(value) {
                propertyTransfer.targetPathLanguage = mapLanguage(value)
            }
        override var propertyName: String?
            get() = propertyTransfer.targetPropertyName
            set(value) {
                propertyTransfer.targetPropertyName = value
            }
        override var stepName: String?
            get() = propertyTransfer.targetStepName
            set(value) {
                propertyTransfer.targetStepName = value
            }
    }

    companion object {
        fun mapLanguage(pathLanguage: PathLanguage): SuuPropertyTransfer.Language {
            return when (pathLanguage) {
                PathLanguage.XQUERY -> SuuPropertyTransfer.Language.XQUERY
                PathLanguage.JSONPATH -> SuuPropertyTransfer.Language.JSONPATH
                else -> SuuPropertyTransfer.Language.XPATH
            }
        }

        fun mapLanguage(language: SuuPropertyTransfer.Language): PathLanguage {
            return when (language) {
                SuuPropertyTransfer.Language.XQUERY -> PathLanguage.XQUERY
                SuuPropertyTransfer.Language.JSONPATH -> PathLanguage.JSONPATH
                else -> PathLanguage.XPATH
            }
        }

    }
}
