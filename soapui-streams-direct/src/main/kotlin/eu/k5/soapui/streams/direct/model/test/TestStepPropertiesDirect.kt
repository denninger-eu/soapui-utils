package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties
import com.eviware.soapui.impl.wsdl.teststeps.addProperty
import com.eviware.soapui.model.support.DefaultTestStepProperty
import eu.k5.soapui.streams.direct.model.PropertiesDirect
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStepProperties

class TestStepPropertiesDirect(
    private val step: WsdlPropertiesTestStep
) : AbstractTestStepDirect(step), SuuTestStepProperties {


    override val properties: SuuProperties
        get() = PropertiesDirect(step, ArrayList()) {

            val newProperty = step.addProperty(it)
            newProperty
        }

}