package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep
import eu.k5.soapui.streams.direct.model.PropertiesDirect
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStep
import java.lang.UnsupportedOperationException

abstract class AbstractTestStepDirect(
    val testStep: WsdlTestStep,
    val propertyFilter: List<String>
) : SuuTestStep {

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


    override val properties: SuuProperties
        get() = PropertiesDirect(testStep, propertyFilter) { throw UnsupportedOperationException(it) }


}


