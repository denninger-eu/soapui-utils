package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.IntLiteral
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.statements.Blank

class MainTransformer(
    private val env: Environment
) {

    fun transform(testCase: SuuTestCase): Scenario {
        val scenario = Scenario(testCase.name)

        val ctx = transformHeader(scenario, testCase)

        for (step in testCase.steps) {
            if (!step.enabled) {
                continue;
            }

            when (step) {
                is SuuTestStepRestRequest -> scenario.statements.add(env.restRequestTransformer.body(step))
                is SuuTestStepPropertyTransfers -> scenario.statements.add(env.transferTransformer.body(step))
                is SuuTestStepDelay -> scenario.statements.add(delay(step, ctx))
                is SuuTestStepProperties -> scenario.statements.add(env.propertiesTransformer.body(step))
                is SuuTestStepScript -> scenario.statements.add(env.scriptTransformer.body(step))
                is SuuTestStepProperties -> {
                }
            }
        }
        return scenario

    }


    private fun delay(step: SuuTestStepDelay, ctx: VariableLiteral): Statement {
        val block = Block("Delay")
        block.statements.add(
            env.base.methodCall(MethodCallExpression(ctx, "sleep", listOf(IntLiteral(step.delay))))
        )
        block.statements.add(Blank())
        return block
    }

    private fun transformHeader(scenario: Scenario, testCase: SuuTestCase): VariableLiteral {

        scenario.addStar(Declaration.loadClass(VariableLiteral("Context"), env.contextClassName))
        scenario.addStar(Declaration.assign(env.ctx, Expression.newInstance(VariableLiteral("Context"))))

        scenario.statements.add(Blank())

        var created = false
        for (step in testCase.steps) {
            if (!step.enabled) {
                continue
            }
            if (step is SuuTestStepRestRequest) {
                scenario.statements.add(env.restRequestTransformer.header(step))
                created = true
            } else if (step is SuuTestStepProperties) {
                scenario.statements.add(env.propertiesTransformer.header(step))
                created = true
            } else if (step is SuuTestStepScript) {
                scenario.statements.add(env.scriptTransformer.header(step));
            }
        }

        if (created) {
            scenario.statements.add(Blank())
        }
        return env.ctx
    }


/*
    private fun asVariable(target: SuuPropertyTransfer.Transfer): VariableLiteral {
        return VariableLiteral(target.stepName + target.propertyName)
    }
*/


    /*   private fun asVariableName(stepName: String): String {
           return stepName
       }*/
}