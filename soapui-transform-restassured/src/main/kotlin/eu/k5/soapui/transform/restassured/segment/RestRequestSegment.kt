package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists
import eu.k5.soapui.streams.model.assertion.SuuAssertionValidStatus
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.MethodRef
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.Declaration
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.expression.Reference
import eu.k5.soapui.transform.restassured.ast.literal.IntVarArgLiteral
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral
import eu.k5.soapui.transform.restassured.model.Environment


class RestRequestSegment(
    private val environment: Environment,
    private val step: SuuTestStepRestRequest
) : Segment {

    override val imports: List<String>
        get() = listOf(
            environment.requestContextFqn(),
            environment.restassuredStar(),
            environment.responseFqn
        )


    override fun write(writer: ModelWriter): ModelWriter {
        val request = Reference("request")

        writer.writeIndention().write(environment.requestContext()).write(" request = context.requestStep(")
            .write(StringLiteral(step.name))
            .write(");").newLine()


        var call = MethodCall("given")



        for (header in step.request.allHeaders()) {
            for (value in header.value) {
                call = call.chain("header", StringLiteral(header.key), StringLiteral(value), indent = 1)
            }
        }

        for (parameter in step.request.parameters.allParameters) {

        }
        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST ||
            step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT
        ) {
            call = call.chain("body", MethodCall(request, "request"), indent = 0)
        }

        call = when (step.baseMethod.httpMethod) {
            SuuRestMethod.HttpMethod.GET -> call.chain("get", MethodCall(request, "url"), indent = 0)
            SuuRestMethod.HttpMethod.POST -> call.chain("post", MethodCall(request, "url"), indent = 0)
            SuuRestMethod.HttpMethod.PUT -> call.chain("put", MethodCall(request, "url"), indent = 0)

            else -> call
        }

        call = call.chain("then", indent = 0)

        writer.write(
            Declaration(
                Reference("response"),
                environment.response,
                call.chain("extract", indent = 1).chain("response")
            )
        )

        writer.write(
            Statement(
                MethodCall(
                    request,
                    MethodRef.withName("status"),
                    listOf(MethodCall("response", MethodRef.withName("statusCode")))
                ).chain(
                    "response", MethodCall("response", MethodRef.withName("asString"))
                )
            )

        )
        writeAssertions(request, writer)

        return writer
    }

    private fun writeAssertions(request: Reference, writer: ModelWriter) {
        for (assertion in step.assertions.assertions) {
            when {
                assertion is SuuAssertionJsonPathExists -> writer.write(
                    Statement(
                        MethodCall(
                            request,
                            MethodRef.withName("assertJsonPathExists"),
                            listOf(StringLiteral(assertion.expression!!), StringLiteral(assertion.expectedContent!!))
                        )
                    )
                )
                assertion is SuuAssertionValidStatus -> writer.write(
                    Statement(
                        MethodCall(
                            request,
                            MethodRef.withName("assertStatus"),
                            listOf(IntVarArgLiteral(assertion.statusCodes.split(",")))
                        )
                    )
                )
            }
        }
    }


}