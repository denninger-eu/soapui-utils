package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.AbstractXmlContainsAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionXmlContains

open class AbstractAssertionXmlDirect (
    private val base: AbstractXmlContainsAssertion
) : AbstractAssertion(base), SuuAssertionXmlContains {

    override var expression: String?
        get() = base.path
        set(value) {
            if (this.base.path != value) {
                this.base.path = value
            }
        }

    override var expectedContent: String?
        get() = this.base.expectedContent
        set(value) {
            if (this.base.expectedContent != value) {
                this.base.expectedContent = value
            }
        }


    override var allowWildcards: Boolean
        get() = this.base.isAllowWildcards
        set(value) {
            if (this.base.isAllowWildcards != value) {
                this.base.isAllowWildcards = value
            }
        }

    override var ignoreNamespaceDifferences: Boolean
        get() = this.base.isIgnoreNamespaceDifferences
        set(value) {
            if (this.base.isIgnoreNamespaceDifferences != value) {
                this.base.isIgnoreNamespaceDifferences = value
            }
        }

    override var ignoreComments: Boolean
        get() = this.base.isIgnoreComments
        set(value) {
            if (this.base.isIgnoreComments != value) {
                this.base.isIgnoreComments = value
            }
        }
}