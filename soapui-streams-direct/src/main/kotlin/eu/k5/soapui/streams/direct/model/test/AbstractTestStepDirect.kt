package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.support.XmlBeansPropertiesTestPropertyHolder
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties
import com.eviware.soapui.impl.wsdl.teststeps.addProperty
import com.eviware.soapui.model.support.DefaultTestStepProperty
import eu.k5.soapui.streams.direct.model.PropertiesDirect
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStep

abstract class AbstractTestStepDirect(
    val testCase: TestCaseDirect,
    val testStep: WsdlTestStep
) : SuuTestStep {

    override var weight: Int = testCase.getWeight(testStep)
    set(value) {
        testCase.setWeight(testStep, value)
    }

    override var enabled: Boolean
        get() = !testStep.isDisabled
        set(value) {
            testStep.isDisabled = !value
        }

    override var name: String
        get() = testStep.name
        set(value) {
            testStep.name = value
        }

    override var description: String?
        get() = testStep.description
        set(value) {
            testStep.description = value
        }
}




