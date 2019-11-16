package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.AbstractXmlContainsAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathAssertionBase
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPath

abstract class AbstractAssertionJsonPathDirect(
    private val base: JsonPathAssertionBase
) : AbstractAssertion(base), SuuAssertionJsonPath {


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

/*    */

}