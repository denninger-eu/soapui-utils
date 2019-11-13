package eu.k5.soapui.streams.listener.copy

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.listener.resource.SyncListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.visitor.listener.SuTestStepListener

class CopyTestSuiteListener(
    private val target: SuProject
) : SuuTestSuiteListener {

    private var targetTestSuite: SuuTestSuite? = null
    private var targetTestCase: SuuTestCase? = null

    override fun enter(suite: SuuTestSuite): VisitResult {
        val target = target.createTestSuite(suite.name)
        target.enabled = suite.enabled

        SyncListener.handleProperties(suite.properties, target.properties)

        targetTestSuite = target
        return VisitResult.CONTINUE
    }

    override fun exit(suite: SuuTestSuite) {
        targetTestSuite = null
    }

    override fun enterTestCase(testCase: SuuTestCase): VisitResult {

        val target = targetTestSuite!!.createTestCase(testCase.name)
        target.enabled = testCase.enabled
        return VisitResult.CONTINUE
    }

    override fun exitTestCase(testCase: SuuTestCase) {
        targetTestCase = null
    }

    override fun createTestStepListener(): SuTestStepListener {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}