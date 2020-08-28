package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.transform.restassured.model.*
import eu.k5.soapui.transform.restassured.model.base.Method
import eu.k5.soapui.transform.restassured.model.base.MethodCall
import eu.k5.soapui.transform.restassured.model.base.Statement
import eu.k5.soapui.transform.restassured.model.base.Visibility

class PropertiesTransformer(
    private val environment: Environment,
    private val scenario: Scenario
) : Transformer<SuuTestStepProperties> {

    override fun transform(step: SuuTestStepProperties) {
        val body = PropertiesMethod(step.name)
        scenario.addImport(environment.propertyHolderFqn())
        for (property in step.properties.properties) {
            body.addProperty(property.name, property.value)
        }
        val method = Method("init" + step.name, body, Visibility.PRIVATE)
        scenario.methods.add(method)
        scenario.init.add(Statement(MethodCall(method)))
    }
}