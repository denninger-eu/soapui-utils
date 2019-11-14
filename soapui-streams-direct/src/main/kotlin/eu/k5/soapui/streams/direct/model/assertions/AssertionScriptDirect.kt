package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.GroovyScriptAssertion
import com.eviware.soapui.security.assertion.InvalidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionScript

class AssertionScriptDirect(
    private val scriptAssertion: GroovyScriptAssertion
) : AbstractAssertion(scriptAssertion), SuuAssertionScript {

    override var script: String?
        get() = scriptAssertion.scriptText
        set(value) {
            if (scriptAssertion.scriptText != value) {
                scriptAssertion.scriptText = value
            }
        }

}