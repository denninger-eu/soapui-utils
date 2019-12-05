package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ReadWsdlServiceDirectTest : AbstractDirectTest() {
    @Test
    fun readWsdlService() {
        val project = testProject("WsdlServiceProject")

        val wsdlServices = project.wsdlServices
        assertFalse(wsdlServices.isEmpty())
        assertWsdlService(wsdlServices[0])
    }

    private fun assertWsdlService(wsdlServices: SuuWsdlService) {
        assertEquals("StockQuoteSoapBinding", wsdlServices.name)
        assertEquals("ServiceDescription", wsdlServices.description)

        assertFalse(wsdlServices.operations.isEmpty())

        assertOperation(wsdlServices.operations[0])
    }

    private fun assertOperation(operation: SuuWsdlOperation) {
        assertEquals("GetLastTradePrice", operation.name)
        assertEquals("OperationDescription", operation.description)

        assertFalse(operation.requests.isEmpty())
        val request = operation.requests[0]

        assertEquals("RequestName", request.name)
        assertEquals("RequestDescription", request.description)
        assertEquals("RequestContent", request.content)

        val headers = request.headers
        assertFalse(headers.isEmpty())

        val header = request.getHeader("HeaderName")
        assertEquals("HeaderValue", header!!.value[0])
        assertEquals("HeaderValue2", header!!.value[1])

    }
}