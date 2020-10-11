package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.NoOpStatement
import eu.k5.soapui.transform.karate.model.statements.Star

class TransformProperties(
    private val environment: Environment
) : Transformer<SuuTestStepProperties> {

    override fun transform(scenario: Scenario, step: SuuTestStepProperties) {
        scenario.inits.add(header(step))
    }

    private fun header(step: SuuTestStepProperties): Statement {
        val block = Block()
        val stepVariable = environment.getVariableForStep(step.name)
        block.statements.add(
            Star(
                Declaration.assign(
                    stepVariable,
                    MethodCallExpression(
                        environment.ctx, environment.createPropertiesStep, listOf(StringLiteral(step.name))
                    )
                )
            )
        )

        for (prop in step.properties.properties) {
            block.statements.add(
                environment.base.methodCall(
                    MethodCallExpression(
                        stepVariable, "setProperty", listOf(
                            StringLiteral(prop.name), StringLiteral(prop.value ?: "")
                        )
                    )
                )
            )
        }
        return block
    }


}
