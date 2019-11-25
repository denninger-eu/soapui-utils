package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.test.*
import org.junit.jupiter.api.Test

class SyncTestStepTest : AbstractSyncTest() {
    
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
            it.transfers[0].source.stepName = "changesSStepName"

            it.transfers[0].target.expression = "changedSource"
            it.transfers[0].target.language = SuuPropertyTransfer.Language.XPATH
            it.transfers[0].target.propertyName = "changedSPropertyName"
            it.transfers[0].target.stepName = "changesSStepName"

            9
        }
    }


    @Test
    fun assertionTestPropertyTransfersInsert() {
        sync<SuuTestStepPropertyTransfers>() {

            val addTransfer = it.addTransfer("newTransfer")

            addTransfer.source.expression = "changedSource"
            addTransfer.source.language = SuuPropertyTransfer.Language.JSONPATH
            addTransfer.source.propertyName = "changedSPropertyName"
            addTransfer.source.stepName = "changesSStepName"

            addTransfer.target.expression = "changedSource"
            addTransfer.target.language = SuuPropertyTransfer.Language.XPATH
            addTransfer.target.propertyName = "changedSPropertyName"
            addTransfer.target.stepName = "changesSStepName"

            1
        }
    }

    @Test
    fun assertionTestStepRestRequest() {
        sync<SuuTestStepRestRequest>() {
            it.description = "changedDescription"
            it.request.content = "changedContent"

            val firstHeader = it.request.headers[0]
            it.request.addOrUpdateHeader(SuuRestRequest.Header(firstHeader.key, "changedValue"))
            3
        }
    }
}