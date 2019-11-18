package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import eu.k5.soapui.streams.model.test.SuuTestStepProperties

class TestStepPropertiesDirect(
    private val step: WsdlPropertiesTestStep
) : AbstractTestStepDirect(step, ArrayList()), SuuTestStepProperties {


}