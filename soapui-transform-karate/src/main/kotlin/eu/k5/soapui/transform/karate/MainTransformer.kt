package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.IntLiteral
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star

class MainTransformer(
    private val env: Environment
) {

    fun transform(testCase: SuuTestCase): Scenario {
        val scenario = Scenario(testCase.name)

        additionals(testCase, scenario)

        scenario.backgrounds.add(Star(Declaration.loadClass(VariableLiteral("Context"), env.contextClassName)))
        scenario.backgrounds.add(Star(Declaration.assign(env.ctx, Expression.newInstance(VariableLiteral("Context")))))


        for (step in testCase.steps) {
            if (!step.enabled) {
                continue;
            }
            when (step) {
                is SuuTestStepRestRequest -> env.restRequestTransformer.transform(scenario, step)
                is SuuTestStepPropertyTransfers -> env.transferTransformer.transform(scenario, step)
                is SuuTestStepDelay -> scenario.bodies.add(delay(step, env.ctx))
                is SuuTestStepProperties -> env.propertiesTransformer.transform(scenario, step)
                is SuuTestStepScript -> env.scriptTransformer.transform(scenario, step)
                is SuuTestStepProperties -> env.propertiesTransformer.transform(scenario, step)
            }
        }
        return scenario

    }


    private fun additionals(testCase: SuuTestCase, scenario: Scenario) {
        val tags = property(testCase, "KARATE_TAGS")
        if (tags != null) {
            scenario.tags.addAll(tags.split("\n"))
        }

        val background = property(testCase, "KARATE_BACKGROUND")
        if (background != null) {
            scenario.postbackground.addAll(background.split("\n"))
        }
        val postexecute = property(testCase, "KARATE_POST")
        if (postexecute != null) {
            scenario.postexecute.addAll(postexecute.split("\n"))
        }
    }

    private fun property(testCase: SuuTestCase, name: String): String? {
        if (testCase.properties.hasProperty(name)) {
            return testCase.properties.byName(name)?.value
        }
        if (testCase.suite.properties.hasProperty(name)) {
            return testCase.suite.properties.byName(name)?.value
        }
        return null
    }

    private fun delay(step: SuuTestStepDelay, ctx: VariableLiteral): Statement {
        val block = Block("Delay")
        block.statements.add(
            env.base.methodCall(MethodCallExpression(ctx, "sleep", listOf(IntLiteral(step.delay))))
        )
        block.statements.add(Blank())
        return block
    }

/*
    private fun transformHeader(scenario: Scenario, testCase: SuuTestCase): VariableLiteral {


        scenario.bodies.add(Blank())

        var created = false
        for (step in testCase.steps) {
            if (!step.enabled) {
                continue
            }
            if (step is SuuTestStepRestRequest) {
                scenario.bodies.add(env.restRequestTransformer.header(step))
                created = true
            } else if (step is SuuTestStepProperties) {
                scenario.bodies.add(env.propertiesTransformer.header(step))
                created = true
            } else if (step is SuuTestStepScript) {
                scenario.bodies.add(env.scriptTransformer.header(step));
            }
        }

        if (created) {
            scenario.bodies.add(Blank())
        }
        return env.ctx
    }

*/

/*
    private fun asVariable(target: SuuPropertyTransfer.Transfer): VariableLiteral {
        return VariableLiteral(target.stepName + target.propertyName)
    }
*/


    /*   private fun asVariableName(stepName: String): String {
           return stepName
       }*/
}