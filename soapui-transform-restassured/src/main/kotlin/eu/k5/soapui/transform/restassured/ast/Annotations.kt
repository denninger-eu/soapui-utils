package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable

class Annotations : Writable {

    val annotations = ArrayList<AnnotationUsage>()


    val imports: List<String> by lazy {
        annotations.map { it.fqn }
    }

    fun add(annotationUsage: AnnotationUsage) {
        annotations.add(annotationUsage)
    }

    class AnnotationUsage(
        val name: String,
        val fqn: String,
        val arguments: List<Expression>
    )

    override fun write(writer: ModelWriter): ModelWriter {
        for (annotation in annotations) {
            writer.writeIndention().write("@").write(annotation.name)
            if (!annotation.arguments.isEmpty()) {
                writer.write("(")
                for (argument in annotation.arguments) {
                    writer.write(argument)
                }
                writer.write(")")
            }
            writer.newLine()
        }
        return writer
    }
}