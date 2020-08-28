package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.restassured.ast.Field
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.StatementList
import eu.k5.soapui.transform.restassured.ast.expression.Assignment
import eu.k5.soapui.transform.restassured.ast.expression.ConstructorCall
import eu.k5.soapui.transform.restassured.ast.expression.FieldAccess
import eu.k5.soapui.transform.restassured.model.*

class DispatchTransformer(
    private val testCase: SuuTestCase
) {
    private val environment = Environment()
    private val scenario = Scenario(environment, testCase.name)
    private val propertiesTransformer = PropertiesTransformer(environment, scenario)
    private val propertyTransfersTransformer = PropertyTransferTransformer(environment, scenario)
    private val restRequestTransformer = RestRequestTransformer(environment, scenario)

    fun transform(): Scenario {
        addContext()

        for (step in testCase.steps) {
            if (!step.enabled) {
                continue;
            }
            when (step) {
                is SuuTestStepProperties -> propertiesTransformer.transform(step)
                is SuuTestStepPropertyTransfers -> propertyTransfersTransformer.transform(step)
                is SuuTestStepRestRequest -> restRequestTransformer.transform(step)
            }
        }

        return scenario
    }

    private fun addContext() {
        scenario.imports.add(environment.contextFqn())
        scenario.fields.add(Field("context", environment.context()))

        scenario.annotations.add(environment.testInstance())
        scenario.annotations.add(environment.extendsWith())
        scenario.annotations.add(environment.testMethodOrder())

        val method = Method("init", StatementList(scenario.init))
        method.annotations.annotations.add(scenario.environment.beforeAll)
        scenario.addMethod(method)

        scenario.init.add(
            Statement(Assignment(FieldAccess("context"), ConstructorCall(environment.context())))
        )
    }
}