package eu.k5.soapui.transform

import java.io.StringWriter
import java.io.Writer

class ModelWriter(
) {
    private val writer = StringWriter()

    private var mainName: String = "main"

    val additionalArtifacts = ArrayList<Artifact>()

    var indent = 0


    fun incIndent(): ModelWriter {
        indent++
        return this
    }

    fun decIndent(): ModelWriter {
        indent--
        return this
    }

    fun writeLine(vararg strings: String): ModelWriter {
        return writeIndention().writeArray(strings).newLine()
    }

    fun writeArray(strings: Array<out String>): ModelWriter {
        for (string in strings) {
            writer.write(string)
        }
        return this
    }

    fun write(strings: String?): ModelWriter {
        if (strings == null) {
            return this
        }
        writer.write(strings)
        return this
    }

    fun write(writable: Writable): ModelWriter = writable.write(this)


    fun writeIndention(): ModelWriter {
        for (index in 0 until indent) {
            write("\t")
        }
        return this
    }

    fun newLine(): ModelWriter {
        writer.write("\n")
        return this
    }

    fun mainContent(): String = writer.toString()

    class Artifact(
        val name: String,
        val content: String
    )
}