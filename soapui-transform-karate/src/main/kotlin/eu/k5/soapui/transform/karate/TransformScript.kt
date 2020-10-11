package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star

class TransformScript(
    private val environment: Environment,
) : Transformer<SuuTestStepScript> {


    override fun transform(scenario: Scenario, step: SuuTestStepScript) {
        scenario.inits.add(header(step))
        if (step.enabled) {
            scenario.bodies.add(body(step))
        }
    }


    private fun header(step: SuuTestStepScript): Statement {
        val block = Block("Script " + step.name)
        val temp = environment.getTempFeatureVariable()

        val artifact = environment.addArtifact(step.name + "Script", step.script ?: "", ".groovy")

        block.statements.add(
            Star(
                DefaultAssignment(
                    "def",
                    Assignment(
                        artifact.variable,
                        MethodCallExpression(null, "read", listOf(StringLiteral(artifact.name)))
                    )
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
                    ).chain("script", listOf(artifact.variable))
                )
            )
        )
        block.statements.add(Blank())
        return block
    }

    private fun body(step: SuuTestStepScript): Block {
        val block = Block("Script " + step.name)
        val stepVariable = environment.getVariableForStep(step.name)

        val tempVariable = environment.getTempFeatureVariable()
        block.statements.add(
            Star(
                Declaration.assign(
                    tempVariable,
                    MethodCallExpression(stepVariable, "execute")
                )
            )
        )
        block.statements.add(Star(DefaultAssignment.exp("print", tempVariable)))
        block.statements.add(Blank())
        return block
    }

}