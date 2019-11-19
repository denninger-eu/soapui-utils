package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.flatten
import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepDelay
import eu.k5.soapui.streams.model.test.SuuTestStepProperties
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.streams.syncWith
import org.junit.Ignore
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SyncTest : AbstractDirectTest() {

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

    @Ignore("Not created in reference soapui project")
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
    fun assertionTestStepProperties() {
        sync<SuuTestStepProperties>() {
            it.description = "changedDescription"
            it.properties.addOrUpdate(it.properties.properties[0].name, "changedValue")
            2
        }
    }

    @Test
    fun assertionTestStepDelay() {
        sync<SuuTestStepDelay>() {
            it.description = "changedDescription"
            it.delay = 666
            2
        }
    }

    @Test
    fun assertionTestPropertyTransfers() {
        sync<SuuTestStepPropertyTransfers>() {
            it.description = "changedDescription"

            it.transfers[0].source.expression = "changedSource"
            it.transfers[0].source.language = SuuPropertyTransfer.Language.JSONPATH
            it.transfers[0].source.propertyName = "changedSPropertyName"
            it.transfers[0].source.stepName ="changesSStepName"

            it.transfers[0].target.expression = "changedSource"
            it.transfers[0].target.language = SuuPropertyTransfer.Language.XPATH
            it.transfers[0].target.propertyName = "changedSPropertyName"
            it.transfers[0].target.stepName ="changesSStepName"

            9
        }
    }

    private inline fun <reified T> sync(createAsync: (T) -> Int) {
        val referenceProject = ProjectDirectTest.testProject("TestSuiteProject")
        val box = AbstractDirectTest.createTempProjectBox("syncAssertion").syncWith(referenceProject)

        val selected = box.flatten().filterIsInstance<T>().first()
        val count = createAsync(selected)

        val beforeSync = AbstractDirectTest.getDifferences(referenceProject, box)
        if (beforeSync.size() != count) {
            throw IllegalArgumentException("Change did not cause $count differences, caused " + beforeSync.size() + ". " + beforeSync.toString())
        }

        box.syncWith(referenceProject)
        val differences = AbstractDirectTest.getDifferences(referenceProject, box)
        assertTrue(differences.isEmpty(), differences.toString())
    }

}