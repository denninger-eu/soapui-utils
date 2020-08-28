package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral

class PropertiesMethod(
    private val name: String
) : Segment {

    private val properties = ArrayList<Property>()

    fun addProperty(name: String, value: String?) {
        properties.add(Property(name, value))
    }

    class Property(
        val key: String,
        val value: String?
    )

    override fun write(writer: ModelWriter): ModelWriter {
        writer.writeIndention().write("PropertyHolder step=context.propertiesStep(").write(StringLiteral(name))
            .write(");")
        writer.newLine()
        for (property in properties) {

            writer.writeIndention().write("step.setProperty(").write(StringLiteral(property.key)).write(",");
            if (property.value != null) {
                writer.write(StringLiteral(property.value))
            } else {
                writer.write("null")
            }
            writer.write(");")
            writer.newLine()
        }
        return writer
    }

    private fun escapeString(string: String): String {
        return string
    }

}