package eu.k5.soapui.transform.karate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BaseTransformerTest {

    private val baseTransformer = BaseTransformer(Environment())

    @Test
    fun escapeVariableName_WithSpace() {
        val escaped = baseTransformer.escapeVariableName("test test")
        Assertions.assertEquals("test_test", escaped)
    }
}