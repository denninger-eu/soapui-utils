package eu.k5.soapui.transform.restassured

import eu.k5.soapui.transform.restassured.ast.Expression
import eu.k5.soapui.transform.restassured.ast.MethodRef
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.expression.Reference
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral
import java.util.regex.Pattern

class BaseTransformer {


    companion object {
        private val ESCAPE_PATTERN = Pattern.compile("""[^_0-9a-zA-Z]""")
        fun escapeVariableName(fileName: String): String {
            return ESCAPE_PATTERN.matcher(fileName).replaceAll("_")
        }

        fun propertyFromContext(value: String): Expression {
            if (value.indexOf("\${") < 0) {
                return StringLiteral(value)
            }
            return MethodCall(Reference("context"), MethodRef.withName("expand"), listOf(StringLiteral(value)))
        }
    }
}