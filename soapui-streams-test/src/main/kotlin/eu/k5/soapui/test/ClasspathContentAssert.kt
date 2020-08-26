package eu.k5.soapui.test

import org.junit.jupiter.api.Assertions
import java.nio.charset.StandardCharsets

class ClasspathContentAssert {

    companion object {
        fun equals(expected: String, actual: String, mode: Mode = Mode.TRIMED) {
            val expectedContent = Thread.currentThread().contextClassLoader.getResourceAsStream(expected).use {
                it.reader(StandardCharsets.UTF_8).readText()
            }


            if (normalize(expectedContent, mode) != normalize(actual, mode)) {
                Assertions.assertEquals(expectedContent, actual)
            }
        }


        private fun normalize(string: String, mode: Mode): String {
            return string.lines().map { it.trim() }.filter { !it.isEmpty() }.joinToString()
        }
    }

    enum class Mode {
        TRIMED
    }
}