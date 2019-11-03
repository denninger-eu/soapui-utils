package eu.k5.soapui.streams.box

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.IOException
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.function.Predicate

class Box(
    val path: Path
) {

    fun findFolderBox(predicate: (Path) -> Boolean): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor(predicate, result))
        return result.map { Box(it) }
    }

    fun <T> load(type: Class<T>): T {
        return load("main", type)
    }

    fun <T> load(section: String, type: Class<T>): T {
        val yaml = Yaml(Constructor(type))

        val section = extractSection(section)
        if (section != null) {
            return yaml.load(section)
        }
        return yaml.load("")
    }

    fun loadSection(section: String): String? {
        return extractSection(section)
    }

    private fun extractSection(section: String): String? {
        val sectionBuilder = StringBuilder()
        var inSection = section == "main"

        for (line in Files.newBufferedReader(path, StandardCharsets.UTF_8).lines()) {
            if (line.startsWith("###")) {
                if (inSection) {
                    return sectionBuilder.toString()
                } else if (line == "### " + section) {
                    inSection = true
                }
            } else {
                if (inSection) {
                    sectionBuilder.append(line).append("\n")
                }
            }
        }
        if (inSection) {
            return sectionBuilder.toString()
        }
        return null
    }


    class FileVisitor(
        private val predicate: (Path) -> Boolean,
        private val result: ArrayList<Path>
    ) : SimpleFileVisitor<Path>() {
        override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
            if (attrs.isRegularFile) {
                if (predicate(file)) {
                    result.add(file)
                }
                return FileVisitResult.SKIP_SUBTREE
            }
            return FileVisitResult.CONTINUE
        }

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            return FileVisitResult.SKIP_SUBTREE
        }
    }

}