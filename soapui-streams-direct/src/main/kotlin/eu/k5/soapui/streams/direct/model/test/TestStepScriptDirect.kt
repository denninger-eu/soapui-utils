package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep
import eu.k5.soapui.streams.model.test.SuuTestStep
import eu.k5.soapui.streams.model.test.SuuTestStepScript

class TestStepScriptDirect(
    testCase: TestCaseDirect,

    private val wsdlGroovyScript: WsdlGroovyScriptTestStep
) : AbstractTestStepDirect(testCase, wsdlGroovyScript),
    SuuTestStepScript {

    override var script: String?
        get() = wsdlGroovyScript.script ?: ""
        set(value) {
            wsdlGroovyScript.script = value
        }


}
