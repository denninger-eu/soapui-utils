package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists
import eu.k5.soapui.streams.model.assertion.SuuAssertionValidStatus
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.*
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star
import java.io.StringWriter
import java.io.Writer

class Exp {

    private val env = Environment()

    fun transform(testCase: SuuTestCase): String {
        val scenario = Scenario(testCase.name)

        val ctx = transformHeader(scenario, testCase)

        for (step in testCase.steps) {
            if (step is SuuTestStepRestRequest) {
                scenario.statements.add(request(step, ctx))
            } else if (step is SuuTestStepPropertyTransfers) {
                scenario.statements.add(transfer(step, ctx))
            } else if (step is SuuTestStepDelay) {
                scenario.statements.add(delay(step, ctx))
            } else if (step is SuuTestStepProperties) {
                scenario.statements.add(properties(step, ctx))
            } else if (step is SuuTestStepScript) {
                scenario.statements.add(script(step, ctx))
            } else if (step is SuuTestStepProperties) {
            }
        }


        val writer: Writer = StringWriter()
        scenario.write(ModelWriter(writer, env))
        val toString = writer.toString()
        println(toString)
        return toString
    }

    private fun script(step: SuuTestStepScript, ctx: VariableLiteral): Statement {
        val block = Block("Script " + step.name)
        val temp = env.getTempFeatureVariable()
        block.statements.add(
            Star(
                Declaration.textAssign(
                    temp, MultiLineStringLiteral(step.script ?: "")
                )
            )
        )
        block.statements.add(
            Star(
                Declaration.assign(
                    env.getTempFeatureVariable(),
                    MethodCallExpression(
                        ctx, "groovy", listOf(
                            temp
                        )
                    )
                )
            )
        )
        block.statements.add(Blank())
        return block
    }

    private fun properties(step: SuuTestStepProperties, ctx: VariableLiteral): Statement {
        return Block(step.name)
    }


    private fun delay(step: SuuTestStepDelay, ctx: VariableLiteral): Statement {
        val block = Block("Delay")
        block.statements.add(
            Star(
                Declaration.assign(
                    env.getTempFeatureVariable(),
                    MethodCallExpression(
                        ctx, "sleep", listOf(
                            IntLiteral(step.delay)
                        )
                    )
                )
            )
        )
        block.statements.add(Blank())
        return block
    }

    fun transformHeader(
        scenario: Scenario,
        testCase: SuuTestCase
    ): VariableLiteral {
        scenario.statements.add(
            Star(
                Declaration.loadClass(
                    VariableLiteral("Context"),
                    "eu.k5.greenfield.karate.Context"
                )
            )
        )
        val ctxVariable = VariableLiteral("ctx")
        scenario.statements.add(
            Star(
                Declaration.assign(
                    ctxVariable,
                    Expression.newInstance(VariableLiteral("Context"))
                )
            )
        )

        scenario.statements.add(Blank())

        var created = false
        for (step in testCase.steps) {
            if (step is SuuTestStepRestRequest) {
                initRestRequestStep(scenario, step, ctxVariable)
                created = true
            } else if (step is SuuTestStepProperties) {
                scenario.statements.add(initProperties(step, ctxVariable))


                created = true
            }
        }

        if (created) {
            scenario.statements.add(Blank())
        }
        return ctxVariable
    }

    private fun initProperties(step: SuuTestStepProperties, ctx: VariableLiteral): Statement {

        val block = Block()
        for (prop in step.properties.properties) {
            block.statements.add(
                Star(
                    Declaration.assign(
                        env.getTempFeatureVariable(),

                        MethodCallExpression(
                            ctx, "setProperty", listOf(
                                StringLiteral(step.name), StringLiteral(prop.name), StringLiteral(prop.value ?: "")
                            )
                        )

                    )

                )
            )

        }
        return block
    }

    private fun initRestRequestStep(
        scenario: Scenario,
        step: SuuTestStepRestRequest,
        ctx: VariableLiteral
    ) {
        var methodCall = MethodCallExpression(
            ctx, "createStep", listOf(StringLiteral(step.name))
        ).chain("url", listOf(StringLiteral(createUrl(step))))

        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT) {
            methodCall = methodCall.chain("request", listOf(StringLiteral(step.request.content ?: "")))
        }
        scenario.statements.add(
            Star(
                Declaration.assign(
                    VariableLiteral(step.name),
                    methodCall
                )
            )
        )
    }

    private fun transfer(step: SuuTestStepPropertyTransfers, ctx: VariableLiteral): Statement {

//        * def t = ctx.transfer("#createResource#response", "$.id", "#Project#projectProperty", "")

        val block = Block(step.name)
        for (transfer in step.transfers) {


            val expression = Declaration.assign(
                env.getTempFeatureVariable(),
                MethodCallExpression(
                    ctx, "transfer", listOf(
                        StringLiteral("#" + transfer.source.stepName + "#" + transfer.source.propertyName),
                        StringLiteral(transfer.source.expression ?: ""),
                        StringLiteral(transfer.target.stepName + transfer.target.propertyName),
                        StringLiteral(transfer.target.expression ?: "")
                    )
                )
            )
            block.statements.add(Star(expression))
        }
        block.statements.add(Blank())
        return block
    }

    private fun asVariable(target: SuuPropertyTransfer.Transfer): VariableLiteral {
        return VariableLiteral(target.stepName + target.propertyName)
    }

    private fun createUrl(step: SuuTestStepRestRequest): String {
        var path = "http://localhost:8080"
        for (resource in step.baseResources) {
            if (!(path.endsWith("/") || resource.path!!.startsWith("/"))) {
                path = "$path/"
            }
            path += resource.path
        }
        for (parameter in step.allParameters()) {
            if (parameter.value.style == SuuRestParameter.Style.TEMPLATE) {
                path = path.replace("{" + parameter.value.name + "}", parameter.value.value)
            }
        }
        return path
    }

    private fun request(step: SuuTestStepRestRequest, ctx: VariableLiteral): RequestBlock {

        var stepVariable = VariableLiteral(step.name)

        val block = RequestBlock(step.name)
        block.Given.expressions.add(DefaultAssignment.exp("url", MethodCallExpression(stepVariable, "url")))
        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST
            || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT
        ) {
            block.Given.expressions.add(
                DefaultAssignment.exp(
                    "request",
                    MethodCallExpression(stepVariable, "request")
                )
            )
        }

        for (parameter in step.allParameters().values) {
            if (parameter.style == SuuRestParameter.Style.QUERY) {
                block.Given.expressions.add(
                    DefaultAssignment.exp(
                        "param",
                        Assignment(
                            ConstantLiteral(parameter.name),
                            MethodCallExpression(ctx, "applyProperties", listOf(StringLiteral(parameter.value)))
                        )
                    )
                )
            } else if (parameter.style == SuuRestParameter.Style.HEADER) {
                block.Given.expressions.add(
                    DefaultAssignment.exp(
                        "header",
                        Assignment(
                            ConstantLiteral(parameter.name),
                            MethodCallExpression(ctx, "applyProperties", listOf(StringLiteral(parameter.value)))
                        )
                    )
                )
            }
        }

        block.Given.expressions.add(
            DefaultAssignment.exp(
                "header",
                Assignment(ConstantLiteral("Accept"), StringLiteral("application/json"))
            )
        )

        block.Given.expressions.add(
            DefaultAssignment.exp(
                "header",
                Assignment(ConstantLiteral("Content-Type"), StringLiteral("application/json"))
            )
        )

        for (header in step.request.headers) {
            for (value in header.value) {
                block.Given.expressions.add(
                    DefaultAssignment.exp(
                        "header",
                        Assignment(
                            ConstantLiteral(header.key),
                            MethodCallExpression(ctx, "applyProperties", listOf(StringLiteral(value)))
                        )
                    )
                )
            }
        }
        block.When.expressions.add(DefaultCall.method(step.baseMethod.httpMethod!!.name))
        block.Then.expressions.add(
            Declaration.assign(
                env.getTempFeatureVariable(),
                MethodCallExpression(
                    stepVariable, "response", listOf(ConstantLiteral("response"))
                )
            )
        )
        for (assertion in step.assertions.assertions) {
            if (assertion is SuuAssertionValidStatus) {
                val codes = assertion.statusCodes.split(" ")
                if (codes.size == 1) {
                    block.Then.expressions.add(
                        DefaultAssignment.exp(
                            "status",
                            ConstantLiteral(codes[0])
                        )
                    )
                }
            } else if (assertion is SuuAssertionJsonPathExists) {
                block.Then.expressions.add(
                    MatchStatement(
                        MethodCallExpression(
                            stepVariable,
                            "assertJsonExists",
                            listOf(StringLiteral(assertion.expression ?: ""))
                        ), ConstantLiteral(
                            assertion.expectedContent ?: ""
                        )
                    )
                )
            }
        }

        return block
    }

    private fun asVariableName(stepName: String): String {
        return stepName
    }
}