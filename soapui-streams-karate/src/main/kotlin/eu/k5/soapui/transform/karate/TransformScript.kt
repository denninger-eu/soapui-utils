package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.karate.model.Block
import eu.k5.soapui.transform.karate.model.Declaration
import eu.k5.soapui.transform.karate.model.MethodCallExpression
import eu.k5.soapui.transform.karate.model.Statement
import eu.k5.soapui.transform.karate.model.literals.MultiLineStringLiteral
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star

class TransformScript(
    private val environment: Environment
) : Transformer<SuuTestStepScript> {

    override fun header(step: SuuTestStepScript): Statement {
        val block = Block("Script " + step.name)
        val temp = environment.getTempFeatureVariable()
        block.statements.add(
            Star(
                Declaration.textAssign(
                    temp, MultiLineStringLiteral(step.script ?: "")
                )
            )
        )

        val stepVariable = environment.getVariableForStep(step.name)

        block.statements.add(
            Star(
                Declaration.assign(
                    stepVariable,
                    MethodCallExpression(
                        environment.ctx, "groovyScript", listOf(
                            StringLiteral(step.name)
                        )
                    ).chain("script", listOf(temp))
                )
            )
        )
        block.statements.add(Blank())
        return block
    }

    override fun body(step: SuuTestStepScript): Statement {
        val block = Block("Script " + step.name)
        val stepVariable = environment.getVariableForStep(step.name)

        block.statements.add(
            Star(
                Declaration.assign(
                    environment.getTempFeatureVariable(),
                    MethodCallExpression(stepVariable, "execute")
                )
            )
        )
        block.statements.add(Blank())
        return block
    }

}