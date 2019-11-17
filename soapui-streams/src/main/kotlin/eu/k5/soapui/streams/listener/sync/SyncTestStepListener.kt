package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.test.*

class SyncTestStepListener(
    private val env: Environment,
    private val reference: SuuTestCase,
    private val target: SuuTestCase
) : SuuTestStepListener {
    override fun createAssertionListener(): SuuAssertionListener {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {


    }

    override fun delay(step: SuuTestStepDelay) {
    }


}