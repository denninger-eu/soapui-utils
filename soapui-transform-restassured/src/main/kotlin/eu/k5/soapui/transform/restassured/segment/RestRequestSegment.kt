package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Comment
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.Declaration
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.expression.NewLine
import eu.k5.soapui.transform.restassured.ast.expression.Reference
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
        val reference = Reference("request")

        writer.writeIndention().write(environment.requestContext()).write(" request=context.requestStep(")
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
            call = call.chain("body", MethodCall(reference, "request"))
        }

        call = when (step.baseMethod.httpMethod) {
            SuuRestMethod.HttpMethod.GET -> call.chain("get", MethodCall(reference, "url"), indent = 0)
            SuuRestMethod.HttpMethod.POST -> call.chain("post", MethodCall(reference, "url"), indent = 0)
            else -> call
        }

        //given().header("Accept", "application/json").get(read.url()).then().statusCode(200).extract().response();

        call = call.chain("then", indent = 0)

        writer.write(Declaration(Reference("response"), environment.response, call.chain("extract").chain("response")))



        return writer.write(Comment("test\nabs\n"))
    }


}