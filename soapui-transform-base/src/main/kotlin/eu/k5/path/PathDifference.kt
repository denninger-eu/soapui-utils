package eu.k5.path

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import kotlin.collections.ArrayList


class PathDifference(
    val reference: Path,
    val target: Path,
    val mode: Mode
) {


    fun getDifferences(): List<Difference> {
        val differences = ArrayList<Difference>()

        val referenceList = flatList(reference)
        val targetList = flatList(target)

        for (reference in referenceList) {
            if (!targetList.contains(reference)) {
                differences.add(Difference("Missing path: $reference"))
            } else {
                contentDifference(differences, reference)
            }
        }

        for (target in targetList) {
            if (!referenceList.contains(target)) {
                differences.add(Difference("Additional path: $target"))
            }
        }
        return differences
    }

    private fun contentDifference(differences: MutableList<Difference>, file: String) {
        val referencePath = reference.resolve(file)
        val targetPath = target.resolve(file)

        if (mode == Mode.EXACT) {

            val referenceBytes = Files.readAllBytes(referencePath)
            val targetBytes = Files.readAllBytes(targetPath)

            if (!Arrays.equals(referenceBytes, targetBytes)) {
                differences.add(Difference("Content mismatch on: $file"))
            }

/*
        } else if (mode == Mode.IGNORE_TRIM) {
            val targetLines = Files.lines(targetPath).map { it.trim() }
            val referenceLines = Files.lines(referencePath).map { it.trim() }

            Streams.forEachPair(referenceLines, targetLines) { ref, target -> if (ref != target) }
*/
        } else {
            TODO("Implement other modes")
        }
    }


    private class FlatFiles(
        private val root: Path,
        private val target: MutableList<String>
    ) : SimpleFileVisitor<Path>() {


        override fun visitFile(
            file: Path,
            attr: BasicFileAttributes
        ): FileVisitResult {
            return if (attr.isRegularFile) {
                val relativize = root.relativize(file)
                target.add(relativize.toString().replace('\\', '/'))
                FileVisitResult.CONTINUE
            } else {
                FileVisitResult.TERMINATE
            }
        }


        override fun visitFileFailed(
            file: Path,
            exc: IOException
        ): FileVisitResult {
            return FileVisitResult.CONTINUE
        }
    }

    enum class Mode {
        EXACT, IGNORE_LINEBREAK, IGNORE_TRIM, IGNORE_ALL_WHITESPACE
    }

    companion object {

        internal fun flatList(path: Path): MutableList<String> {
            val result = ArrayList<String>()
            Files.walkFileTree(path, FlatFiles(path, result))
            return result
        }

    }

}