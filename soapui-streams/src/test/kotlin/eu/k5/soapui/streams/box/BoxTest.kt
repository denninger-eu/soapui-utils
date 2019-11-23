package eu.k5.soapui.streams.box

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class BoxTest {

    @Test
    fun `escapeFileName no special characters`() {
        assertEquals(" _-test123TEST(){}[]", BoxImpl.escapeFileName(" _-test123TEST(){}[]"))
    }

    @Test
    fun `escapeFileName colon is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName(":"))
    }

    @Test
    fun `escapeFileName slash is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName("/"))
    }

    @Test
    fun `escapeFileName backslash is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName("\\"))
    }

    @Test
    fun `escapeFileName pipe is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName("|"))
    }

    @Test
    fun `escapeFileName qmark is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName("?"))
    }

    @Test
    fun `escapeFileName asterisk is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName("*"))
    }

    @Test
    fun `escapeFileName lt gt is escaped`() {
        assertEquals("__", BoxImpl.escapeFileName("<>"))
    }

    @Test
    fun `escapeFileName quote is escaped`() {
        assertEquals("_", BoxImpl.escapeFileName("\""))
    }

}
