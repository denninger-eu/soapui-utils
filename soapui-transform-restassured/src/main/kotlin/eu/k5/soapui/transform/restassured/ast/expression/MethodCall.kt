package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.MethodRef

class MethodCall(
    private val instance: Expression?,
    private val method: MethodRef,
    private val arguments: List<Expression>,
    private val indentChained: Int = -1
) : Expression {

    constructor(instance: String, method: MethodRef) : this(Reference(instance), method, ArrayList())

    constructor(method: MethodRef) : this(null, method, ArrayList())
    constructor(name: String) : this(MethodRef.withName(name))

    constructor(reference: Reference, name: String) : this(reference, MethodRef.withName(name), ArrayList())

    override fun write(writer: ModelWriter): ModelWriter {
        writer.pushIndent()
        if (instance != null) {
            writer.write(instance)
            if (indentChained >= 0) {
                writer.incIndent(indentChained).newLine()
                writer.writeIndention().write(".")
            } else {
                writer.write(".")
            }
        }


        writer.write(method.name).write("(");
        for (argument in arguments.withIndex()) {
            writer.write(argument.value)
            if (argument.index < arguments.size - 1) {
                writer.write(", ")
            }
        }
        writer.write(")");
        writer.popIndent()
        return writer
    }

    fun chain(name: String, vararg arguments: Expression, indent: Int = -1): MethodCall {
        return MethodCall(this, MethodRef.withName(name), arguments.toList(), indent)
    }


}