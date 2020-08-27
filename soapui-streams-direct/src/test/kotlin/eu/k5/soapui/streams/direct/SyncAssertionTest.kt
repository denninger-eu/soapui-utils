package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.test.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SyncAssertionTest : AbstractSyncTest() {

    @Test
    fun assertionInvalidStatus() {
        sync<SuuAssertionInvalidStatus>() {
            it.statusCodes = "500"
            1
        }
    }

    @Test
    fun assertionValidStatus() {
        sync<SuuAssertionValidStatus>() {
            it.statusCodes = "500"
            1
        }
    }

    @Test
    fun assertionContains() {
        sync<SuuAssertionContains>() {
            it.caseSensitive = !it.caseSensitive
            it.content = "changed"
            it.regexp = !it.regexp
            3
        }
    }

    @Test
    fun assertionNotContains() {
        sync<SuuAssertionNotContains>() {
            it.caseSensitive = !it.caseSensitive
            it.content = "changed"
            it.regexp = !it.regexp
            3
        }
    }


    @Test
    fun assertionScript() {
        sync<SuuAssertionScript>() {
            it.script = "changed"
            1
        }
    }

    @Test
    fun assertionDuration() {
        sync<SuuAssertionDuration>() {
            it.time = "changed"
            1
        }
    }

    @Test
    fun assertionJsonPathCount() {
        sync<SuuAssertionJsonPathCount>() {
            it.expression = "changedExpression"
            it.expectedContent = "changedExpectedContent"
            2
        }
    }

    @Disabled("Not created in reference soapui project")
    @Test
    fun assertionJsonPathExists() {
        sync<SuuAssertionJsonPathExists>() {
            it.expression = "changedExpression"
            it.expectedContent = "changedExpectedContent"
            2
        }
    }

    @Test
    fun assertionJsonPathMatch() {
        sync<SuuAssertionJsonPathMatch>() {
            it.expression = "changedExpression"
            it.expectedContent = "changedExpectedContent"
            2
        }
    }

    @Test
    fun assertionJsonPathRegEx() {
        sync<SuuAssertionJsonPathRegEx>() {
            it.expression = "changedExpression"
            it.expectedContent = "changedExpectedContent"
            it.regularExpression = "changedRegEx"
            3
        }
    }


    @Test
    fun assertionXPath() {
        sync<SuuAssertionXPath>() {
            it.expression = "changedExpression"
            it.expectedContent = "changedExpectedContent"
            2
        }
    }


    @Test
    fun assertionXQuery() {
        sync<SuuAssertionXQuery>() {
            it.expression = "changedExpression"
            it.expectedContent = "changedExpectedContent"
            2
        }
    }


    @Test
    fun assertionTestStepRestRequest_addHeader() {
        sync<SuuTestStepRestRequest>() {
            it.request.addOrUpdateHeader(Header("newHeader", "newValue"))
            1
        }
    }


}