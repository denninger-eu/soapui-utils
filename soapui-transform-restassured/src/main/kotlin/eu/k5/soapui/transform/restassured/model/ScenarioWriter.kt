package eu.k5.soapui.transform.restassured.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.model.base.Visibility
import java.util.*
import kotlin.collections.ArrayList

class ScenarioWriter(
    private val scenario: Scenario,
    private val writer: ModelWriter
) {


    fun write() {

        val imports = ArrayList(scenario.imports)
        Collections.sort(imports)

        for (import in imports) {
            writer.write("import ").write(import).write(";")
            writer.newLine()
        }

        writer.write("class ").write(scenario.name).write("Test").write(" {")
        writer.newLine()
        writer.incIndent()

        writeFields()

        writeInit()

        for (method in scenario.methods) {
            method.write(writer)
        }
        writer.decIndent()
        writer.write("}")

    }

    private fun writeFields() {

        for (field in scenario.fields) {
            writer.writeIndention().write(Visibility.PRIVATE.keyword).write(" ").write(field.type).write(" ")
                .write(field.name).write(";").newLine()
        }

    }

    private fun writeInit() {
        if (scenario.init.isEmpty()) {
            return
        }

        writer.writeLine("public void init(){")
        writer.incIndent()
        for (init in scenario.init) {
            init.write(writer)
        }

        writer.decIndent()

        writer.writeLine("}")

    }

}