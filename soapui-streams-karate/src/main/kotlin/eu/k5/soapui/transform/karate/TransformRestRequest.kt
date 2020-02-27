package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists
import eu.k5.soapui.streams.model.assertion.SuuAssertionValidStatus
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.ConstantLiteral
import eu.k5.soapui.transform.karate.model.statements.NoOpStatement
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.statements.Star

class TransformRestRequest(
    private val environment: Environment
) : Transformer<SuuTestStepRestRequest> {


    override fun header(step: SuuTestStepRestRequest): Statement {
        val block = Block()
        var methodCall = MethodCallExpression(
            environment.ctx, environment.createRequestStep, listOf(StringLiteral(step.name))
        ).chain("url", listOf(StringLiteral(createUrl(step))))

        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST
            || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT
            || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PATCH
        ) {

            val content = step.request.content
            if (!content.isNullOrBlank()) {
                val artifact = environment.addArtifact(step.name, content)
                block.statements.add(
                    Star(
                        DefaultAssignment(
                            "text",
                            Assignment(
                                artifact.variable,
                                MethodCallExpression(null, "read", listOf(StringLiteral(artifact.name)))
                            )
                        )
                    )
                )
                methodCall = methodCall.chain("request", listOf(artifact.variable))
            }

        }

        val stepVariable = environment.getVariableForStep(step.name)
        block.statements.add(
            Star(Declaration.assign(stepVariable, methodCall))
        )
        return block
    }

    private fun createUrl(step: SuuTestStepRestRequest): String {
        var path = environment.baseUrl
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

    override fun body(step: SuuTestStepRestRequest): Statement {

        var stepVariable = environment.getVariableForStep(step.name)

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
                            environment.base.propertyFromContext(parameter.value)
                        )
                    )
                )
            } else if (parameter.style == SuuRestParameter.Style.HEADER) {
                block.Given.expressions.add(
                    DefaultAssignment.exp(
                        "header",
                        Assignment(
                            ConstantLiteral(parameter.name),
                            environment.base.propertyFromContext(parameter.value)
                        )
                    )
                )
            }
        }


        for (header in step.request.headers) {
            for (value in header.value) {
                block.Given.expressions.add(
                    DefaultAssignment.exp(
                        "header",
                        Assignment(
                            ConstantLiteral(header.key),
                            environment.base.propertyFromContext(value)
                        )
                    )
                )
            }
        }
        block.When.expressions.add(DefaultCall.method(step.baseMethod.httpMethod!!.name))
        block.Then.expressions.add(
            DefaultAssignment(
                "print", MethodCallExpression(stepVariable, "response", listOf(ConstantLiteral("response")))
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

}