package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.Environment
import java.io.Writer

class ModelWriter(
    private val writer: Writer,
    val env: Environment
) {
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

    fun write(strings: String): ModelWriter {
        writer.write(strings)
        return this
    }

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
}