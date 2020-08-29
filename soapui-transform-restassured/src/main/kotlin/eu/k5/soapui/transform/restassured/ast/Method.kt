package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable

class Method(
    override val name: String,
    private val body: Segment,
    private val visibility: Visibility = Visibility.PUBLIC,
    private val returnType: String = "void"
) : MethodRef, Writable {

    val annotations = Annotations()

    val imports: List<String>
        get() {
            val list = ArrayList(body.imports)
            list.addAll(annotations.imports)
            return list
        }


    override fun write(writer: ModelWriter): ModelWriter {
        writer.write(annotations)
        writer.writeIndention().write(visibility.keyword).write(" ").write(returnType).write(" ").write(name)
            .write("(){")
        writer.incIndent()
        writer.newLine()
        writer.write(body)
        writer.decIndent()
        writer.writeLine("}")
        writer.newLine()
        return writer
    }

}
