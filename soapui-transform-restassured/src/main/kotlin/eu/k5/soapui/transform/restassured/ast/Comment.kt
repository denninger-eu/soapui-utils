package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter

class Comment(
    private val comment: String
) : Segment {
    override fun write(writer: ModelWriter): ModelWriter {
        val lines = comment.split("\n")
        if (lines.size > 1) {
            writer.writeIndention().write("/*  ").write(lines[0]).newLine()
            writer.incIndent()
            for (line in lines.subList(1, lines.size)) {
                writer.writeIndention().write(line).newLine()
            }
            writer.decIndent()
            writer.writeLine("*/")
        } else {
            return writer.writeIndention().write("/* ").write(comment).write(" */").newLine()
        }
        return writer
    }

    override val imports: List<String>
        get() = emptyList()

}