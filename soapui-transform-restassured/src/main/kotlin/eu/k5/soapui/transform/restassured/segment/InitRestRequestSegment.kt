package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.extensions.createUrl
import eu.k5.soapui.transform.restassured.ast.MethodRef
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.expression.Reference
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral
import eu.k5.soapui.transform.restassured.Environment

class InitRestRequestSegment(
    private val env: Environment,
    private val step: SuuTestStepRestRequest
) : Segment {

    override val imports: List<String>
        get() = listOf(env.requestContextFqn())


    override fun write(writer: ModelWriter): ModelWriter {

        writer.writeIndention().write(env.requestContext()).write(" request=context.requestStep(")
            .write(StringLiteral(step.name))
            .write(");").newLine()

        var initRequest = MethodCall(
            Reference("request"),
            MethodRef.withName("url"),
            listOf(StringLiteral(step.createUrl(env.baseUrl)))
        )

        if (step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.POST ||
            step.baseMethod.httpMethod == SuuRestMethod.HttpMethod.PUT
        ) {
            initRequest = initRequest.chain("request", StringLiteral(step.request.content!!))
        }


        writer.write(Statement(initRequest))
        return writer
    }


}