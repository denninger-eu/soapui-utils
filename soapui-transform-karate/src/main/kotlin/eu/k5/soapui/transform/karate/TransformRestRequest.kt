package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists
import eu.k5.soapui.streams.model.assertion.SuuAssertionValidStatus
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.extensions.createUrl
import eu.k5.soapui.transform.karate.model.*
import eu.k5.soapui.transform.karate.model.literals.ConstantLiteral
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.statements.Star

class TransformRestRequest(
    private val environment: Environment
) : Transformer<SuuTestStepRestRequest> {


    override fun transform(scenario: Scenario, step: SuuTestStepRestRequest) {
        scenario.inits.add(header(step))
        scenario.bodies.add(body(step))
    }


    private fun header(step: SuuTestStepRestRequest): Statement {
        val block = Block()
        var methodCall = MethodCallExpression(
            environment.ctx, environment.createRequestStep, listOf(StringLiteral(step.name))
        ).chain("url", listOf(StringLiteral(step.createUrl(environment.baseUrl))))



        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST
            || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT
            || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PATCH
        ) {

            val content = step.request.content
            if (!content.isNullOrBlank()) {
                val artifact = environment.addArtifact(step.name + "Request", content, ".txt")
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
                methodCall = methodCall.chain("request", listOf(artifact.variable))
            }

        }
        for (parameter in step.allParameters()) {
            if (parameter.value.style == SuuRestParameter.Style.TEMPLATE) {
                methodCall = methodCall.chain(
                    "property",
                    listOf(StringLiteral(parameter.value.name), StringLiteral(parameter.value.value))
                )
            }
        }

        val stepVariable = environment.getVariableForStep(step.name)
        block.statements.add(
            Star(Declaration.assign(stepVariable, methodCall))
        )
        return block
    }


    fun body(step: SuuTestStepRestRequest): Statement {

        var stepVariable = environment.getVariableForStep(step.name)

        val block = RequestBlock(step.name)
        block.blockComments.add(Comment(step.baseMethod.httpMethod.toString() + " " + step.createUrl("")))
        block.given.expressions.add(DefaultAssignment.exp("url", MethodCallExpression(stepVariable, "url")))
        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST
            || step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT
        ) {
            block.given.expressions.add(
                DefaultAssignment.exp(
                    "request",
                    MethodCallExpression(stepVariable, "request")
                )
            )
        }

        for (parameter in step.allParameters().values) {
            if (parameter.value.isNullOrEmpty()) {
                continue
            }
            if (parameter.style == SuuRestParameter.Style.QUERY) {
                block.given.expressions.add(
                    DefaultAssignment.exp(
                        "param",
                        Assignment(
                            ConstantLiteral(parameter.name),
                            environment.base.propertyFromContext(parameter.value)
                        )
                    )
                )
            } else if (parameter.style == SuuRestParameter.Style.HEADER) {
                block.given.expressions.add(
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
                block.given.expressions.add(
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

        var responseAssign = MethodCallExpression(stepVariable, "response", listOf(ConstantLiteral("response")))
        responseAssign = MethodCallExpression(responseAssign, "status", listOf(ConstantLiteral("responseStatus")))
        block.then.methodCall(responseAssign)

        for (assertion in step.assertions.assertions) {
            if (assertion is SuuAssertionValidStatus) {
                val codes = assertion.statusCodes.split(" ")
                if (codes.size == 1) {
                    block.then.expressions.add(
                        DefaultAssignment.exp(
                            "status",
                            ConstantLiteral(codes[0])
                        )
                    )
                } else {
                    block.then.methodCall(
                        MethodCallExpression(
                            stepVariable,
                            "assertStatus",
                            listOf(StringLiteral(assertion.statusCodes ?: ""))
                        )
                    )
                }
            } else if (assertion is SuuAssertionJsonPathExists) {
                block.then.methodCall(
                    MethodCallExpression(
                        stepVariable,
                        "assertJsonPathExists",
                        listOf(
                            StringLiteral(assertion.expression ?: ""), StringLiteral(assertion.expectedContent ?: "")
                        )
                    )
                )
            }
        }

        return block
    }

}