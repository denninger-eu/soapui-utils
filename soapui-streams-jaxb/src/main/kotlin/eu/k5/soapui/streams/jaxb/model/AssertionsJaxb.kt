package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.AssertionElement
import eu.k5.soapui.streams.model.assertion.*

class AssertionsJaxb(
    elements: List<AssertionElement>
) : SuuAssertions {


    override val assertions: List<SuuAssertion> = elements.map { map(it) }

    override fun createInvalidStatus(name: String): SuuAssertionInvalidStatus {
        TODO("Not yet implemented")
    }

    override fun createValidStatus(name: String): SuuAssertionValidStatus {
        TODO("Not yet implemented")
    }

    override fun createContains(name: String): SuuAssertionContains {
        TODO("Not yet implemented")
    }

    override fun createNotContains(name: String): SuuAssertionNotContains {
        TODO("Not yet implemented")
    }

    override fun createDuration(name: String): SuuAssertionDuration {
        TODO("Not yet implemented")
    }

    override fun createScript(name: String): SuuAssertionScript {
        TODO("Not yet implemented")
    }

    override fun createJsonPathExists(name: String): SuuAssertionJsonPathExists {
        TODO("Not yet implemented")
    }

    override fun createJsonPathMatch(name: String): SuuAssertionJsonPathMatch {
        TODO("Not yet implemented")
    }

    override fun createJsonPathCount(name: String): SuuAssertionJsonPathCount {
        TODO("Not yet implemented")
    }

    override fun createJsonPathRegEx(name: String): SuuAssertionJsonPathRegEx {
        TODO("Not yet implemented")
    }

    override fun createXPath(name: String): SuuAssertionXPath {
        TODO("Not yet implemented")
    }

    override fun createXQuery(name: String): SuuAssertionXQuery {
        TODO("Not yet implemented")
    }

    override fun createSoapResponse(name: String): SuuAssertionSoapResponse {
        TODO("Not yet implemented")
    }

    open class AssertionJaxb(
        val element: AssertionElement
    ) : SuuAssertion {
        override var name: String
            get() = element.name ?: ""
            set(value) {}
        override var enabled: Boolean
            get() = true
            set(value) {}

    }

    class AssertionInvalidStatusJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionInvalidStatus {

        override var statusCodes: String
            get() = element.options["codes"] ?: ""
            set(value) {}
    }

    class AssertionValidStatusJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionValidStatus {

        override var statusCodes: String
            get() = element.options["codes"] ?: ""
            set(value) {}
    }


    companion object {

        fun map(element: AssertionElement): AssertionJaxb {
            if (element.type == "Invalid HTTP Status Codes") {
                return AssertionInvalidStatusJaxb(element)
            } else if (element.type == "Valid HTTP Status Codes") {
                return AssertionValidStatusJaxb(element)
            }
            return AssertionJaxb((element))
        }
    }
}