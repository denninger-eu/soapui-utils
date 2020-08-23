package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter

class Comment(
    val comment: String
) : Statement() {

    override fun write(writer: ModelWriter) : ModelWriter {
        val commentLines = comment.split("\n")
        for(commentLine in commentLines){
            writer.writeIndention().write("# ").write(commentLine).newLine()
        }
        return writer
    }

}