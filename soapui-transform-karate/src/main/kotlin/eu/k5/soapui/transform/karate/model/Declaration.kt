package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter
import eu.k5.soapui.transform.karate.model.literals.JavaClassLiteral
import eu.k5.soapui.transform.karate.model.literals.Literal
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import eu.k5.soapui.transform.karate.model.literals.write

class Declaration(
    private val variableName: VariableLiteral,
    private val value: Expression,
    private val def: String = "def"
) : Expression {


    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(def).write(" ").write(variableName).write(" = ").write(value)
    }

    companion object {
        fun response(variableName: String): Declaration =
            Declaration(
                VariableLiteral(variableName),
                VariableLiteral("response")
            )

        fun variable(variable: VariableLiteral, value: Literal): Declaration {
            return Declaration(variable, value)
        }

        fun loadClass(variable: VariableLiteral, className: String): Declaration {
            return Declaration(variable, JavaClassLiteral(className))
        }

        fun assign(variableLiteral: VariableLiteral, newInstance: Expression): Expression {
            return Declaration(variableLiteral, newInstance)
        }

        fun textAssign(variable: VariableLiteral, literal: Literal): Expression {
            return Declaration(variable, literal, "text")
        }
    }
}