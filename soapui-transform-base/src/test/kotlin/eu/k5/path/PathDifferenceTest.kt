package eu.k5.path

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class PathDifferenceTest {

    @Test
    fun `flatList on not existing path`() {
        val result = PathDifference.flatList(Paths.get("notexisting"))
        assertTrue(result.isEmpty())
    }

    @Test
    fun `flatList on existing path`() {
        val result = PathDifference.flatList(Paths.get("src/test/resources", "pathdifference", "flatlist"))
        val expected = ArrayList<String>()
        expected.add("fileinroot.txt")
        expected.add("folder/fileinfolder.txt")
        expected.add("folder/folderinfolder/fileinfolderinfolder.txt")
        assertEquals(expected, result)
    }


    @Test
    fun `getDifference with path differences`() {
        val differences = createPathdifferen("withdifference", PathDifference.Mode.EXACT).getDifferences()

        val expected = ArrayList<String>()
        expected.add("Missing path: referenceonly.txt")
        expected.add("Additional path: targetonly.txt")
        assertEquals(expected, differences.map { it.description })
    }

    @Test
    fun `getDifference with content differences`() {
        val differences = createPathdifferen("contentdifference", PathDifference.Mode.EXACT).getDifferences()

        val expected = ArrayList<String>()
        expected.add("Content mismatch on: spacedifference.txt")
        assertEquals(expected, differences.map { it.description })
    }


    companion object {
        fun createPathdifferen(name: String, mode: PathDifference.Mode): PathDifference {
            val reference = Paths.get("src/test/resources", "pathdifference", name, "reference")
            val target = Paths.get("src/test/resources", "pathdifference", name, "target")
            return PathDifference(reference, target, mode)
        }

    }
}