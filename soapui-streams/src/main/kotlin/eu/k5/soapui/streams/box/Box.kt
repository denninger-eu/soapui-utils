package eu.k5.soapui.streams.box

import org.yaml.snakeyaml.Yaml
import java.nio.file.Path

interface Box {

    val path: Path

    fun findSubFolderBox(predicate: (Path) -> Boolean): List<Box>

    fun findFolderBox(predicate: (Path) -> Boolean): List<Box>

    fun findOrderFiles(): List<Box>


    fun createFolder(folder: String, fileName: String): Box

    fun createFile(name: String, fileExtension: String): Box

    fun createOrderedFile(pattern: String, name: String): Box


    fun write(instance: Any): Box

    fun <T> write(type: Class<T>, instance: T): Box

    fun write(instance: Any, section: String): Box

    fun writeSection(section: String, content: String?)


    fun load(): Any

    fun <T> load(type: Class<T>): T

    fun <T> load(type: Class<T>, section: String): T?

    fun loadSection(section: String): String?

}