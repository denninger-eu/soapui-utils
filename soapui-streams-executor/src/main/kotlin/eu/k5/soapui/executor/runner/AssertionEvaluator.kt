package eu.k5.soapui.executor.runner

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.PathNotFoundException
import eu.k5.soapui.streams.model.assertion.*
import java.lang.RuntimeException

class AssertionEvaluator(
    private val assertions: List<SuuAssertion>,
    private val response: ClientResponse
) {

    private val jsonDocument: DocumentContext by lazy { JsonPath.parse(response.body) }

    fun evaluate(): List<AssertionFault> {
        val faults = ArrayList<AssertionFault>()
        for (assertion in assertions) {
            try {
                evaluate(assertion)
            } catch (error: AssertError) {
                faults.add(AssertionFault(assertion.name, error.expected, error.actual))
            }
        }
        return faults
    }

    private fun evaluate(assertion: SuuAssertion) {
        when (assertion) {
            is SuuAssertionXPath -> eval(assertion)
            is SuuAssertionJsonPathMatch -> eval(assertion)
            is SuuAssertionJsonPathExists -> eval(assertion)
            is SuuAssertionJsonPathCount -> eval(assertion)

            else -> throw AssertError("unknown", "")
        }
    }

    private fun eval(assertion: SuuAssertionXPath) {

    }

    private fun eval(assertion: SuuAssertionJsonPathMatch) {
        val result = jsonDocument.read<String>(assertion.expression)
        if (result != assertion.expectedContent) {
            throw AssertError(assertion.expectedContent ?: "", result)
        }
    }


    private fun eval(assertion: SuuAssertionJsonPathExists) {
        try {
            val result = jsonDocument.read<String?>(assertion.expression)
            if (result != null) {
                if (!assertion.expectsExists()) {
                    throw AssertError("not exists", result)
                }
            } else {
                if (assertion.expectsExists()) {
                    throw AssertionError("exists", result)
                }
            }
        } catch (notFound: PathNotFoundException) {
            if (assertion.expectsExists()) {
                throw AssertError("exists", "")
            }
        }
    }
    private fun eval(assertion: SuuAssertionJsonPathCount) {
        val result = jsonDocument.read<String?>(assertion.expression)

    }

    private class AssertError(
        val expected: String,
        val actual: String
    ) : RuntimeException() {

    }
}