package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.Visibility
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.model.Environment
import eu.k5.soapui.transform.restassured.model.Scenario
import eu.k5.soapui.transform.restassured.segment.InitRestRequestSegment
import eu.k5.soapui.transform.restassured.segment.RestRequestSegment

class RestRequestTransformer(
    private val environment: Environment,
    private val scenario: Scenario
) : Transformer<SuuTestStepRestRequest> {

    override fun transform(step: SuuTestStepRestRequest) {
        val fieldName = BaseTransformer.escapeVariableName(step.name)


        val initMethod = Method("init$fieldName", InitRestRequestSegment(environment, step), Visibility.PRIVATE)
        scenario.addMethod(initMethod)
        scenario.init.add(Statement(MethodCall(initMethod)))

        val requestMethod = Method(fieldName, RestRequestSegment(environment, step))
        requestMethod.annotations.add(environment.test)
        requestMethod.annotations.add(environment.displayName(step.name))
        requestMethod.annotations.add(environment.dependsOn(step.name))
        scenario.addMethod(requestMethod)

    }

}