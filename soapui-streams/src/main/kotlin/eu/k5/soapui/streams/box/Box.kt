package eu.k5.soapui.streams.box

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.TypeDescription
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
import java.util.regex.Pattern

class Box(
    private val path: Path,
    private val mode: Mode = Mode.WRITE
) {

    fun findSubFolderBox(predicate: (Path) -> Boolean): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor(predicate, result, 2))
        return result.map { Box(it) }
    }

    fun findFolderBox(predicate: (Path) -> Boolean): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor(predicate, result, 1))
        return result.map { Box(it) }
    }


    fun findOrderFiles(): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor({ NUMBER.matcher(it.fileName.toString()).matches() }, result, 1))
        return result.map { Box(it) }
    }

    fun <T> load(type: Class<T>): T {
        return load("main", type)
    }

    private fun <T> load(section: String, type: Class<T>): T {
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

    fun write(yaml: Yaml, instance: Any): Box {
        return write(yaml, instance, "main")
    }

    fun <T> write(type: Class<T>, instance: T): Box {

        val constructor = Constructor(type)
        constructor.addTypeDescription(TypeDescription(type))
        val binder = Yaml(options)
        val yaml = binder.dump(instance)
        writeSection("main", yaml)
        return this
    }

    private fun loadSections(): LinkedHashMap<String, String> {
        if (!Files.exists(path)) {
            return LinkedHashMap()
        }
        val sections = LinkedHashMap<String, String>()
        val sectionBuilder = StringBuilder()
        var section = "main"
        var inSection = section == "main"

        for (line in Files.newBufferedReader(path, StandardCharsets.UTF_8).lines()) {
            if (line.startsWith("###")) {

                val matcher = SECTION_PATTERN.matcher(line)
                if (matcher.matches()) {
                    val newSection = matcher.group("section")
                    sections[section] = sectionBuilder.toString()
                    sectionBuilder.clear()
                    section = newSection
                }
            } else {
                if (inSection) {
                    sectionBuilder.append(line).append("\n")
                }
            }
        }
        if (inSection) {
            sections[section] = sectionBuilder.toString()
        }
        return sections
    }

    private fun extractSection(section: String): String? {
        val sectionBuilder = StringBuilder()
        var inSection = section == "main"

        for (line in Files.newBufferedReader(path, StandardCharsets.UTF_8).lines()) {
            if (line.startsWith("###")) {
                if (inSection) {
                    return sectionBuilder.toString()
                } else if (line == "### $section") {
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

    fun createFolder(folder: String, fileName: String): Box {
        val resolve = path.parent.resolve(folder)
        check(!Files.exists(resolve)) { "Already exists" }
        Files.createDirectories(resolve)
        return Box(resolve.resolve(fileName))
    }


    fun createFile(name: String, fileExtension: String): Box {
        val resolved = path.parent.resolve(name + fileExtension)
        check(!Files.exists(resolved)) { "Already exists" }
        return Box(resolved)
    }

    fun createOrderedFile(pattern: String, name: String): Box {
        val index = findFileIndex() + 10

        val fileName = String.format(pattern, index, name)
        val resolved = path.parent.resolve(fileName)
        check(!Files.exists(resolved)) { "Already exists" }
        return Box(resolved)
    }

    private fun findFileIndex(): Int {
        return Files.list(path.parent).map { getWeight(it) }.max(Integer::compare).orElse(0)
    }

    private fun getWeight(file: Path): Int {
        val matcher = NUMBER.matcher(file.fileName.toString())
        return if (matcher.matches()) {
            Integer.parseInt(matcher.group("number"))
        } else {
            0
        }
    }


    fun writeSection(section: String, content: String?) {
        val sections = loadSections()
        sections[section] = content ?: ""
        writeSections(sections)
    }

    private fun writeSections(sections: Map<String, String>) {
        var first = true
        Files.newBufferedWriter(path, StandardCharsets.UTF_8).use {
            for ((section, sectionContent) in sections.entries) {
                if (first && section == "main") {
                    it.write(sectionContent)
                    first = true
                } else {
                    it.write("### $section\n")
                    it.write(sectionContent)
                }
            }
        }
    }

    fun load(yaml: Yaml): Any {
        val section = extractSection("main")
        if (section != null) {
            return yaml.load(section)
        }
        return yaml.load("")
    }

    fun <T> load(yaml: Yaml, type: Class<T>): T? {
        return load(yaml, type, "main")
    }

    fun <T> load(yaml: Yaml, type: Class<T>, section: String): T? {

        val section = extractSection(section)
        if (section != null) {
            return type.cast(yaml.load(section))
        }
        return null
    }

    fun write(yaml: Yaml, instance: Any, section: String): Box {
        val dump = yaml.dump(instance)
        writeSection(section, dump)
        return this
    }


    class FileVisitor(
        private val predicate: (Path) -> Boolean,
        private val result: ArrayList<Path>,
        private val searchLevel: Int
    ) : SimpleFileVisitor<Path>() {
        private var level = 0
        override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
            if (level == searchLevel && attrs.isRegularFile) {
                if (predicate(file)) {
                    result.add(file)
                }
                return FileVisitResult.SKIP_SUBTREE
            }
            return FileVisitResult.CONTINUE
        }

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            level--
            return FileVisitResult.SKIP_SUBTREE
        }

        override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            if (level <= 1) {
                level++
                return FileVisitResult.CONTINUE
            } else {
                return FileVisitResult.SKIP_SUBTREE
            }
        }
    }

    companion object {

        private val NUMBER = Pattern.compile("(?<number>[0-9]{1,5}).*")
        private val SECTION_PATTERN = Pattern.compile("### (?<section>[a-zA-Z0-9]{1,16})")

        val options: DumperOptions
            get() {
                val options = DumperOptions()
                options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                return options
            }

    }

    enum class Mode {
        READ_ONLY, WRITE
    }

}