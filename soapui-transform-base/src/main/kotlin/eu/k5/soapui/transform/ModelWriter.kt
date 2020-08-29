package eu.k5.soapui.transform

import java.io.StringWriter
import java.util.*
import kotlin.collections.ArrayList

class ModelWriter(
) {
    private val writer = StringWriter()

    private var mainName: String = "main"

    val additionalArtifacts = ArrayList<Artifact>()

    var indent = ArrayDeque<Int>()
    var column = 0

    init {
        indent.push(0)
    }

    fun pushIndent(): ModelWriter {
        indent.push(column / 4)
        return this
    }

    fun popIndent(): ModelWriter {
        indent.pop()
        return this
    }

    fun incIndent(): ModelWriter {
        val top = indent.pop()
        indent.push(top + 1)
        return this
    }

    fun incIndent(by: Int): ModelWriter {
        val top = indent.pop()
        indent.push(top + by)
        return this
    }

    fun decIndent(): ModelWriter {
        val top = indent.pop()
        indent.push(top - 1)
        return this
    }

    fun writeLine(vararg strings: String): ModelWriter {
        column = 0
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
        column += strings.length
        return this
    }

    fun write(writable: Writable): ModelWriter = writable.write(this)


    fun writeIndention(): ModelWriter {
        for (index in 0 until indent.peek()) {
            write("\t")
            column += 4
        }

        return this
    }

    fun newLine(): ModelWriter {
        writer.write("\n")
        column = 0
        return this
    }

    fun mainContent(): String = writer.toString()

    class Artifact(
        val name: String,
        val content: String
    )

    fun addArtifact(name: String, content: String) = additionalArtifacts.add(Artifact(name, content))
}