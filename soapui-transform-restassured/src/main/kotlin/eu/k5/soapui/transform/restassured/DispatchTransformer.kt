package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.transform.restassured.ast.Field
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.Assignment
import eu.k5.soapui.transform.restassured.ast.expression.ConstructorCall
import eu.k5.soapui.transform.restassured.ast.expression.FieldAccessExpression
import eu.k5.soapui.transform.restassured.model.*

class DispatchTransformer(
    private val testCase: SuuTestCase
) {
    private val scenario = Scenario(testCase.name)
    private val environment = Environment()
    private val propertiesTransformer = PropertiesTransformer(environment, scenario)
    private val propertyTransfersTransfomrer = PropertyTransferTransformer(environment, scenario)

    fun transform(): Scenario {
        addContext()

        for (step in testCase.steps) {
            if (!step.enabled) {
                continue;
            }
            when (step) {
                is SuuTestStepProperties -> propertiesTransformer.transform(step)
                is SuuTestStepPropertyTransfers -> propertyTransfersTransfomrer.transform(step)
            }
        }

        return scenario
    }

    private fun addContext() {
        scenario.imports.add(environment.contextFqn())
        scenario.fields.add(Field("context", environment.context()))
        scenario.init.add(
            Statement(Assignment(FieldAccessExpression("context"), ConstructorCall(environment.context())))
        )
    }
}