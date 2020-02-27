package eu.k5.soapui.transform.karate

import eu.k5.soapui.transform.karate.model.DefaultAssignment
import eu.k5.soapui.transform.karate.model.Expression
import eu.k5.soapui.transform.karate.model.MethodCallExpression
import eu.k5.soapui.transform.karate.model.Statement
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.statements.Star
import java.util.regex.Pattern

class BaseTransformer(
    private val environment: Environment
) {

    fun escapeVariableName(fileName: String): String {
        return ESCAPE_PATTERN.matcher(fileName).replaceAll("_")
    }

    fun propertyFromContext(value: String): Expression {
        if (value.indexOf("\${") < 0) {
            return StringLiteral(value)
        }
        return MethodCallExpression(environment.ctx, environment.expandProperties, listOf(StringLiteral(value)))
    }

    fun methodCall(call: MethodCallExpression): Statement {
        return Star(DefaultAssignment("print", call))
    }

    companion object {
        private val ESCAPE_PATTERN = Pattern.compile("""[^_0-9a-zA-Z]""")
    }
}