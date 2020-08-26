package eu.k5.soapui.transform.restassured.model

import eu.k5.soapui.transform.ModelWriter

class PropertiesMethod(
    private val name: String
) : MethodBody() {

    private val properties = ArrayList<Property>()

    fun addProperty(name: String, value: String?) {
        properties.add(Property(name, value))
    }

    class Property(
        val key: String,
        val value: String?
    )

    override fun write(writer: ModelWriter) {
        writer.writeIndention().write("PropertyHolder step=context.propertiesStep(\"").write(name).write("\");")
        writer.newLine()
        for (property in properties) {

            writer.writeIndention().write("step.setProperty(\"").write(property.key).write("\",");
            if (property.value != null) {
                writer.write("\"").write(escapeString(property.value)).write("\"")
            } else {
                writer.write("null")
            }
            writer.write(");")
            writer.newLine()
        }
    }

    private fun escapeString(string: String): String {
        return string
    }

}