package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.literals.ConstantLiteral
import eu.k5.soapui.transform.karate.model.literals.StringLiteral

class DefaultAssignment(
    val variable: String,
    val value: Expression
) : Expression {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(variable).write(" ").write(value)
    }


    companion object {

        fun url(value: String): DefaultAssignment {
            return DefaultAssignment("url", StringLiteral(value))
        }

        fun exp(value: String, expression: Expression): DefaultAssignment {
            return DefaultAssignment(value, expression)
        }

        fun method(method: String): Expression {
            return DefaultAssignment("method", ConstantLiteral(method))
        }

        fun request(value: String): Expression {
            return DefaultAssignment("request", ConstantLiteral(value))
        }

        fun variable(name: String, value: String): Expression {
            return DefaultAssignment(name, StringLiteral(value))
        }
    }
}