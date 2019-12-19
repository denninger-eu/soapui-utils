package eu.k5.soapui.executor

import eu.k5.soapui.executor.runner.AssertionEvaluator
import eu.k5.soapui.executor.runner.ClientResponse
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathCount
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathExists
import eu.k5.soapui.streams.model.assertion.SuuAssertionJsonPathMatch
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.Test

class AssertionEvaluatorTest {

    @Test
    fun jsonPath_valid() {
        val jsonPath = JsonPathMatch("$.self", "id")
        val result = AssertionEvaluator(listOf(jsonPath), response("{\"self\":\"id\"}")).evaluate()
        assertTrue(result.isEmpty())
    }

    @Test
    fun jsonPath_withFault() {
        val jsonPath = JsonPathMatch("$.self", "idx")
        val result = AssertionEvaluator(listOf(jsonPath), response("{\"self\":\"id\"}")).evaluate()
        assertEquals(1, result.size)
        assertEquals("id", result[0].actual)
    }

    @Test
    fun jsonPathExists() {
        val jsonPath = JsonPathExists("$.self", "true")
        val result = AssertionEvaluator(listOf(jsonPath), response("{\"self\":\"id\"}")).evaluate()
        assertTrue(result.isEmpty())
    }

    @Test
    fun jsonPathExists_fault() {
        val jsonPath = JsonPathExists("$.seslf", "false")
        val result = AssertionEvaluator(listOf(jsonPath), response("{\"self\":\"id\"}")).evaluate()
        assertTrue(result.isEmpty())
    }

    @Test
    fun jsonPathCount(){
        val jsonPath = JsonPathCount("$.self", "1")
        val result = AssertionEvaluator(listOf(jsonPath), response("{\"self\":\"id\"}")).evaluate()
        assertTrue(result.isEmpty())

    }

    private fun response(body: String): ClientResponse {
        return ClientResponse(body)
    }


    class JsonPathMatch(
        override var expression: String?,
        override var expectedContent: String?,
        override var name: String = "name",
        override var enabled: Boolean = true
    ) : SuuAssertionJsonPathMatch

    class JsonPathExists(
        override var expression: String?,
        override var expectedContent: String?,
        override var name: String = "name",
        override var enabled: Boolean = true
    ) : SuuAssertionJsonPathExists

    class JsonPathCount(
        override var expression: String?,
        override var expectedContent: String?,
        override var name: String = "name",
        override var enabled: Boolean = true
    ) : SuuAssertionJsonPathCount
}