package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.transform.restassured.ast.*
import eu.k5.soapui.transform.restassured.ast.expression.*
import eu.k5.soapui.transform.restassured.segment.Scenario

class DispatchTransformer(
    private val testCase: SuuTestCase
) {
    private val environment = Environment()
    private val scenario = Scenario(environment, testCase.name)

    private val propertiesTransformer = PropertiesTransformer(environment, scenario)
    private val propertyTransfersTransformer = PropertyTransferTransformer(environment, scenario)
    private val restRequestTransformer = RestRequestTransformer(environment, scenario)
    private val scriptTransformer = ScriptTransformer(environment, scenario)

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
                is SuuTestStepScript -> scriptTransformer.transform(step)
            }
        }

        return scenario
    }

    private fun readExtendsFrom(): String? {
        return listOf(
            testCase.properties.byName("RestassuredExtends"),
            testCase.suite.properties.byName("RestassuredExtends")
        ).firstOrNull { it != null }?.value
    }

    private fun readInitMethod(): String? {
        return listOf(
            testCase.properties.byName("RestassuredInitMethod"),
            testCase.suite.properties.byName("RestassuredInitMethod")
        ).firstOrNull { it != null }?.value
    }

    private fun addContext() {

        scenario.extendsFrom = readExtendsFrom()
        scenario.imports.add(environment.contextFqn())
        scenario.fields.add(Field("context", environment.context()))

        scenario.annotations.add(environment.testInstance())
        scenario.annotations.add(environment.extendsWith())
        scenario.annotations.add(environment.testMethodOrder())

        val method = Method("init", StatementList(scenario.init))
        method.annotations.annotations.add(scenario.environment.beforeAll)
        scenario.addMethod(method)

        val init = if (readInitMethod() == null) {
            ConstructorCall(environment.context(), listOf(Reference("this")))
        } else {
            MethodCall(
                Reference("super"), MethodRef.withName(readInitMethod()!!),
                listOf(
                    ConstructorCall(environment.context(), listOf(Reference("this")))
                )
            )
        }

        scenario.init.add(
            Statement(
                Assignment(
                    FieldAccess("context"),
                    init
                )
            )
        )
    }
}