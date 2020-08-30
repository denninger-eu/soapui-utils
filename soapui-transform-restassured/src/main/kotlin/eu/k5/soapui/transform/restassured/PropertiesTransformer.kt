package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.Visibility
import eu.k5.soapui.transform.restassured.segment.PropertiesMethod
import eu.k5.soapui.transform.restassured.segment.Scenario

class PropertiesTransformer(
    private val environment: Environment,
    private val scenario: Scenario
) : Transformer<SuuTestStepProperties> {

    override fun transform(step: SuuTestStepProperties) {
        val body = PropertiesMethod(environment, step.name)
        for (property in step.properties.properties) {
            body.addProperty(property.name, property.value)
        }
        val method = Method("init" + BaseTransformer.escapeVariableName(step.name), body, Visibility.PRIVATE)
        scenario.addMethod(method)
        scenario.init.add(Statement(MethodCall(method)))
    }
}