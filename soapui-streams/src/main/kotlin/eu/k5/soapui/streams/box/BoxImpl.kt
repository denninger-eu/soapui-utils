package eu.k5.soapui.streams.box

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.IOException
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.regex.Pattern

class BoxImpl(
    override val path: Path,
    private val mode: Mode = Mode.WRITE
) : Box {

    override fun findSubFolderBox(predicate: (Path) -> Boolean): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor(predicate, result, 2))
        return result.map { BoxImpl(it) }
    }

    override fun findFolderBox(predicate: (Path) -> Boolean): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor(predicate, result, 1))
        return result.map { BoxImpl(it) }
    }


    override fun findOrderFiles(): List<Box> {
        val result = ArrayList<Path>()
        Files.walkFileTree(path.parent, FileVisitor({ NUMBER.matcher(it.fileName.toString()).matches() }, result, 1))
        return result.map { BoxImpl(it) }
    }

    override fun <T> load(type: Class<T>): T {
        return load("main", type)
    }

    override fun toString(): String {
        return path.toAbsolutePath().toString()
    }

    private fun <T> load(section: String, type: Class<T>): T {

        val yaml = YamlContext.YAML_LOAD


        val section = extractSection(section)
        if (section != null) {
            return yaml.load(section)
        }
        return yaml.load("")
    }

    override fun loadSection(section: String): String? {
        return extractSection(section)
    }

    override fun write(instance: Any): Box {
        return write(instance, "main")
    }

    override fun <T> write(type: Class<T>, instance: T): Box {
        val binder = YamlContext.YAML_DUMPER
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

    override fun createFolder(folder: String, fileName: String): Box {
        var resolve: Path? = null
        for (index in 0..10) {
            var resolveFolder: String =
                if (index != 0) {
                    folder + index
                } else {
                    folder
                }
            resolve = path.parent.resolve(escapeFileName(resolveFolder))
            if (!Files.exists(resolve)) {
                break
            }
        }
        check(!Files.exists(resolve!!)) { "Already exists: $resolve" }
        Files.createDirectories(resolve)
        return BoxImpl(resolve.resolve(fileName))
    }


    override fun createFile(name: String, fileExtension: String): Box {
        val resolved = path.parent.resolve(escapeFileName(name) + fileExtension)
        check(!Files.exists(resolved)) { "Already exists" }
        return BoxImpl(resolved)
    }

    override fun createOrderedFile(pattern: String, name: String): Box {
        val index = findFileIndex() + 10

        val fileName = String.format(pattern, index, escapeFileName(name))
        val resolved = path.parent.resolve(fileName)
        check(!Files.exists(resolved)) { "Already exists" }
        return BoxImpl(resolved)
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


    override fun writeSection(section: String, content: String?) {
        val sections = loadSections()
        if (content.isNullOrEmpty()) {
            sections.remove(section)
        } else if (content.endsWith("\n")) {
            sections[section] = content
        } else {
            sections[section] = content + "\n"
        }
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

    override fun load(): Any {
        val section = extractSection("main")
        if (section != null) {
            return yamlLoader.load(section)
        }
        return yamlLoader.load("")
    }


    override fun <T> load(type: Class<T>, section: String): T? {

        val section = extractSection(section)
        if (section != null) {
            return type.cast(yamlLoader.load(section))
        }
        return null
    }

    override fun write(instance: Any, section: String): Box {
        val dump = YamlContext.YAML_DUMPER.dump(instance)
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
        private val ESCAPE_PATTERN = Pattern.compile("""[^-_0-9a-zA-Z{}()\]\[ ]""")
        private val NUMBER = Pattern.compile("(?<number>[0-9]{1,5}).*")
        private val SECTION_PATTERN = Pattern.compile("### (?<section>[a-zA-Z0-9]{1,16})")

        val options: DumperOptions
            get() {
                val options = DumperOptions()
                options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                return options
            }

        fun escapeFileName(fileName: String): String {
            return ESCAPE_PATTERN.matcher(fileName).replaceAll("_")
        }

        inline fun changed(original: String?, update: String?): Boolean {
            if (original.isNullOrEmpty() && update.isNullOrEmpty()) {
                return false
            }
            return original != update
        }

        inline fun changed(original: Boolean?, update: Boolean?, default: Boolean = true): Boolean {
            if (default) {
                if (trueOrNull(original) && trueOrNull(update)) {
                    return false
                }
                return original != update
            } else {
                if (falseOrNull(original) && falseOrNull(update)) {
                    return false
                }
                return original != update
            }
        }

        inline fun trueOrNull(value: Boolean?): Boolean {
            return value == null || value
        }


        inline fun falseOrNull(value: Boolean?): Boolean {
            return value == null || !value
        }

        private val yamlLoader = YamlContext.YAML_LOAD

    }

    enum class Mode {
        READ_ONLY, WRITE

    }

}