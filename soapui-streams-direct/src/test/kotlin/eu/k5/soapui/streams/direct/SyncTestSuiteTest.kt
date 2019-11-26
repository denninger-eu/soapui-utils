package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestStepDelay
import eu.k5.soapui.streams.model.test.SuuTestSuite
import org.junit.jupiter.api.Test

class SyncTestSuiteTest : AbstractSyncTest() {

    @Test
    fun changeTestCaseName() {
        sync<SuuTestCase>() {
            println(it.name)
            it.name = "changedName"

            1
        }
    }


}