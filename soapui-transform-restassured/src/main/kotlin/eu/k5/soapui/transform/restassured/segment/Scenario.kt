package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.*
import eu.k5.soapui.transform.restassured.Environment
import java.util.*
import kotlin.collections.ArrayList

class Scenario(
    val environment: Environment,
    val name: String
) : TestClass(Visibility.PUBLIC) {

    val init = ArrayList<Statement>()

    override fun write(writer: ModelWriter): ModelWriter {

        val imports = ArrayList(imports)
        Collections.sort(imports)

        for (import in allImports()) {
            writer.write("import ").write(import).write(";")
            writer.newLine()
        }

        writer.newLine()

        writer.write(annotations)
        writer.write(visibility.keyword).write(" class ").write(name).write("Test")
        if (extendsFrom != null) {
            writer.write(" extends ").write(extendsFrom!!)
        }
        writer.write(" {")
        writer.newLine()
        writer.incIndent()

        writeFields(writer)

        for (method in methods) {
            method.write(writer)
        }
        writer.decIndent()
        writer.write("}")

        return writer
    }

}