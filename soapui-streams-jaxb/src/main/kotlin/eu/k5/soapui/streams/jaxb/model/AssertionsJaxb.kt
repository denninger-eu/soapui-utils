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
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var enabled: Boolean
            get() = true
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionInvalidStatusJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionInvalidStatus {

        override var statusCodes: String
            get() = element.options["codes"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
    }

    class AssertionValidStatusJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionValidStatus {
        override var statusCodes: String
            get() = element.options["codes"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
    }


    class AssertionScriptJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionScript {

        override var script: String?
            get() = element.options["scriptText"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionDurationJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionDuration {

        override var time: String?
            get() = element.options["SLA"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }


    class AssertionContainsJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionContains {

        override var content: String?
            get() = element.options["token"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var regexp: Boolean
            get() = element.options["useRegEx"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var caseSensitive: Boolean
            get() = element.options["ignoreCase"].toBoolean().not()
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionNotContainsJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionNotContains {

        override var content: String?
            get() = element.options["token"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var regexp: Boolean
            get() = element.options["useRegEx"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var caseSensitive: Boolean
            get() = element.options["ignoreCase"].toBoolean().not()
            set(value) {
                TODO("Not yet implemented: $value")
            }
    }

    class AssertionJsonPathMatchJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionJsonPathMatch {

        override var expression: String?
            get() = element.options["path"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var expectedContent: String?
            get() = element.options["content"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }


    class AssertionJsonPathExistsJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionJsonPathExists {

        override var expression: String?
            get() = element.options["path"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var expectedContent: String?
            get() = element.options["content"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionJsonPathCountJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionJsonPathCount {

        override var expression: String?
            get() = element.options["path"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var expectedContent: String?
            get() = element.options["content"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionJsonPathRegExJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionJsonPathRegEx {

        override var expression: String?
            get() = element.options["path"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var expectedContent: String?
            get() = element.options["content"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var regularExpression: String?
            get() = element.options["regEx"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionXPathJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionXPath {

        override var expression: String?
            get() = element.options["path"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var expectedContent: String?
            get() = element.options["content"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

        override var allowWildcards: Boolean
            get() = element.options["allowWildcards"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var ignoreNamespaceDifferences: Boolean
            get() = element.options["ignoreNamspaceDifferences"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var ignoreComments: Boolean
            get() = element.options["ignoreComments"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    class AssertionXQueryJaxb(
        element: AssertionElement
    ) : AssertionJaxb(element), SuuAssertionXQuery {

        override var expression: String?
            get() = element.options["path"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var expectedContent: String?
            get() = element.options["content"] ?: ""
            set(value) {
                TODO("Not yet implemented: $value")
            }

        override var allowWildcards: Boolean
            get() = element.options["allowWildcards"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var ignoreNamespaceDifferences: Boolean
            get() = element.options["ignoreNamspaceDifferences"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }
        override var ignoreComments: Boolean
            get() = element.options["ignoreComments"].toBoolean()
            set(value) {
                TODO("Not yet implemented: $value")
            }

    }

    companion object {

        fun map(element: AssertionElement): AssertionJaxb {
            if (element.type == "Invalid HTTP Status Codes") {
                return AssertionInvalidStatusJaxb(element)
            } else if (element.type == "Valid HTTP Status Codes") {
                return AssertionValidStatusJaxb(element)
            } else if (element.type == "GroovyScriptAssertion") {
                return AssertionScriptJaxb(element)
            } else if (element.type == "Response SLA Assertion") {
                return AssertionDurationJaxb(element)
            } else if (element.type == "Simple Contains") {
                return AssertionContainsJaxb(element)
            } else if (element.type == "Simple NotContains") {
                return AssertionNotContainsJaxb(element)
            } else if (element.type == "JsonPath Existence Match") {
                return AssertionJsonPathExistsJaxb(element)
            } else if (element.type == "JsonPath Match") {
                return AssertionJsonPathMatchJaxb(element)
            } else if (element.type == "JsonPath Count") {
                return AssertionJsonPathCountJaxb(element)
            } else if (element.type == "JsonPath RegEx Match") {
                return AssertionJsonPathRegExJaxb(element)
            } else if (element.type == "XPath Match") {
                return AssertionXPathJaxb(element)
            } else if (element.type == "XQuery Match") {
                return AssertionXQueryJaxb(element)
            }

            TODO("Missing support for assertion :" + element.type)


        }
    }
}