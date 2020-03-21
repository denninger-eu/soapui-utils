package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.*
import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathContentAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathCountAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathExistenceAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathRegExAssertion
import com.eviware.soapui.impl.wsdl.teststeps.assertions.soap.SoapResponseAssertion
import com.eviware.soapui.model.testsuite.TestAssertion
import com.eviware.soapui.security.assertion.InvalidHttpStatusCodesAssertion
import com.eviware.soapui.security.assertion.ValidHttpStatusCodesAssertion
import eu.k5.soapui.streams.model.assertion.*

class AssertionsDirect(
    private val assertionList: MutableList<TestAssertion>,
    private val addAssertion: (String) -> TestAssertion
) : SuuAssertions {
    override fun createSoapResponse(name: String): SuuAssertionSoapResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*
        "JMS Status" -> "JMS Status"
        "WS-Security Status" -> "WSS Status Assertion"
        "XQuery Match" -> "XQuery Match"
        "Not SOAP Fault" -> "SOAP Fault Assertion"
        "WS-Addressing Request" -> "WS-A Request Assertion"
        "SOAP Fault" -> "Not SOAP Fault Assertion"
        "Cross Site Scripting Detection" -> "CrosSiteScript"
        "JMS Timeout" -> "JMS Timeout"
        "JDBC Timeout" -> "JDBC Timeout"
        "JsonPath RegEx Match" -> "JsonPath RegEx Match"
        "Sensitive Information Exposure" -> "Sensitive Information Exposure"
        "WS-Addressing Response" -> "WS-A Response Assertion"
        "SOAP Response" -> "SOAP Response"
        "HTTP Download all resources" -> "HTTP Download all resources"
        "Schema Compliance" -> "Schema Compliance"
        "SOAP Request" -> "SOAP Request"
        "JDBC Status" -> "JDBC Status"*/
    override fun createJsonPathExists(name: String): SuuAssertionJsonPathExists {
        return AssertionJsonPathExistsDirect(createAssertion("JsonPath Existence Match", name))
    }

    override fun createJsonPathMatch(name: String): SuuAssertionJsonPathMatch {
        return AssertionJsonPathMatchDirect(createAssertion("JsonPath Match", name))
    }

    override fun createJsonPathCount(name: String): SuuAssertionJsonPathCount {
        return AssertionJsonPathCountDirect(createAssertion("JsonPath Count", name))
    }

    override fun createJsonPathRegEx(name: String): SuuAssertionJsonPathRegEx {
        return AssertionJsonPathRegExDirect(createAssertion("JsonPath RegEx Match", name))
    }

    override fun createXPath(name: String): SuuAssertionXPath {
        return AssertionXPathDirect(createAssertion("XPath Match", name))
    }

    override fun createXQuery(name: String): SuuAssertionXQuery {
        return AssertionXQueryDirect(createAssertion("XQuery Match", name))
    }

    override fun createInvalidStatus(name: String): SuuAssertionInvalidStatus {
        return AssertionInvalidStatusDirect(createAssertion("Invalid HTTP Status Codes", name))
    }

    override fun createValidStatus(name: String): SuuAssertionValidStatus {
        return AssertionValidStatusDirect(createAssertion("Valid HTTP Status Codes", name))
    }

    override fun createContains(name: String): SuuAssertionContains {
        return AssertionContainsDirect(createAssertion("Contains", name))
    }

    override fun createNotContains(name: String): SuuAssertionNotContains {
        return AssertionNotContainsDirect(createAssertion("Not Contains", name))
    }

    override fun createDuration(name: String): SuuAssertionDuration {
        return AssertionDurationDirect(createAssertion("Response SLA", name))
    }

    override fun createScript(name: String): SuuAssertionScript {
        return AssertionScriptDirect(createAssertion("Script Assertion", name))
    }

    private inline fun <reified T> createAssertion(type: String, name: String): T {
        val testAssertion = addAssertion(type) as WsdlMessageAssertion
        testAssertion.name = name
        return testAssertion as T
    }

    override val assertions: List<SuuAssertion>
        get() = assertionList.map { mapAssertion(it) }

    companion object {

        fun mapAssertion(testAssertion: TestAssertion): SuuAssertion {
            return when (testAssertion) {
                is InvalidHttpStatusCodesAssertion -> AssertionInvalidStatusDirect(testAssertion)
                is ValidHttpStatusCodesAssertion -> AssertionValidStatusDirect(testAssertion)
                is GroovyScriptAssertion -> AssertionScriptDirect(testAssertion)
                is ResponseSLAAssertion -> AssertionDurationDirect(testAssertion)
                is SimpleContainsAssertion -> AssertionContainsDirect(testAssertion)
                is SimpleNotContainsAssertion -> AssertionNotContainsDirect(testAssertion)
                is JsonPathContentAssertion -> AssertionJsonPathMatchDirect(testAssertion)
                is JsonPathExistenceAssertion -> AssertionJsonPathExistsDirect(testAssertion)
                is JsonPathCountAssertion -> AssertionJsonPathCountDirect(testAssertion)
                is JsonPathRegExAssertion -> AssertionJsonPathRegExDirect(testAssertion)
                is XQueryContainsAssertion -> AssertionXQueryDirect(testAssertion)
                is XPathContainsAssertion -> AssertionXPathDirect(testAssertion)
                is SoapResponseAssertion -> AssertionSoapResponseDirect(testAssertion)
                else -> TODO("Unsupported assertion: " + testAssertion.javaClass.toString())
            }
        }


    }
}