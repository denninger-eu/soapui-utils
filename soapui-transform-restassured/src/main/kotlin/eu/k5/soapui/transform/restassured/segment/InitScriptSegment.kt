package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.extensions.createUrl
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.MethodRef
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.Declaration
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.expression.Reference
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral
import eu.k5.soapui.transform.restassured.model.Environment

class InitScriptSegment(
    private val env: Environment,
    private val step: SuuTestStepScript
) : Segment {

    override val imports: List<String>
        get() = listOf(env.scriptContextFqn)


    override fun write(writer: ModelWriter): ModelWriter {






        return writer
    }


}