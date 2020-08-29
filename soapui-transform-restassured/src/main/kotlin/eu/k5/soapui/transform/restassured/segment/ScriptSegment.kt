package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral
import eu.k5.soapui.transform.restassured.model.Environment


class ScriptSegment(
    private val environment: Environment,
    private val step: SuuTestStepScript
) : Segment {

    override val imports: List<String>
        get() = emptyList()

    override fun write(writer: ModelWriter): ModelWriter {
        writer.write(
            Statement(
                MethodCall(environment.context, Method.named("groovyScript"), listOf(StringLiteral(step.name)))
                    .chain("execute")
            )
        )
        return writer

    }

}