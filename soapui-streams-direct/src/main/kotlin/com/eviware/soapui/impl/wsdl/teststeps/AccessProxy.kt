package com.eviware.soapui.impl.wsdl.teststeps

import com.eviware.soapui.model.testsuite.TestProperty

fun addProperty(testStep: WsdlTestStepWithProperties, property: TestProperty) {
    testStep.addProperty(property)
}