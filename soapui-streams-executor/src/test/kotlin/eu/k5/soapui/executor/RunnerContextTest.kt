package eu.k5.soapui.executor

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RunnerContextTest {

    @Test
    fun applyProperties_noProperty() {
        val context = RunnerContext()
        val applied = context.applyProperties("property")
        assertEquals("property", applied)
    }

    @Test
    fun applyProperties_standaloneProperty() {
        val context = RunnerContext()
        context.addProperty("property", "value")
        val applied = context.applyProperties("\${property}")
        assertEquals("value", applied)
    }

    @Test
    fun applyProperties_inlineProperty() {
        val context = RunnerContext()
        context.addProperty("property", "value")
        val applied = context.applyProperties("prefix \${property} suffix")
        assertEquals("prefix value suffix", applied)
    }

    @Test
    fun applyProperties_scoped() {
        val context = RunnerContext()
        context.addProperty("#Project#property", "value")
        val applied = context.applyProperties("prefix \${#Project#property} suffix")
        assertEquals("prefix value suffix", applied)
    }

    @Test
    fun applyProperties_inlineTwiceProperty() {
        val context = RunnerContext()
        context.addProperty("property", "value")
        val applied = context.applyProperties("prefix \${property}\${property} suffix")
        assertEquals("prefix valuevalue suffix", applied)
    }
}