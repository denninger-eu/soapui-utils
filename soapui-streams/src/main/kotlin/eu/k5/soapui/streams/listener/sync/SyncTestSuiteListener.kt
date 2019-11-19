package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.copy.CopyTestSuiteListener
import eu.k5.soapui.streams.model.test.SuuTestStepListener

class SyncTestSuiteListener(
    private val environment: Environment,
    private val referenceProject: SuProject,
    private val targetProject: SuProject?
) : SuuTestSuiteListener {

    private var referenceTestSuite: SuuTestSuite? = null
    private var targetTestCase: SuuTestCase?=null
    private var referenceTestCase: SuuTestCase? = null

    override fun enter(targetSuite: SuuTestSuite): VisitResult {
        val ref = referenceProject.getTestSuite(targetSuite.name)
        if (ref == null) {
            return VisitResult.TERMINATE
        }
        targetSuite.enabled = ref.enabled
        referenceTestSuite = ref
        return VisitResult.CONTINUE
    }

    override fun exit(suite: SuuTestSuite) {
        val missingTestCases = ArrayList(referenceTestSuite!!.testCases)
        suite.testCases.forEach { existing -> missingTestCases.removeIf { existing.name == it.name } }

        val copyListener = CopyTestSuiteListener(targetProject!!, suite)
        for (missingTestCase in missingTestCases) {

            missingTestCase.apply(copyListener)
        }
    }

    override fun enterTestCase(targetTestCase: SuuTestCase): VisitResult {
        val ref = referenceTestSuite!!.getTestCase(targetTestCase.name)
        if (ref == null) {
            return VisitResult.TERMINATE
        }
        targetTestCase.enabled = ref.enabled
        referenceTestCase = ref
        this.targetTestCase = targetTestCase
        return VisitResult.CONTINUE
    }

    override fun exitTestCase(testCase: SuuTestCase) {
        referenceTestCase = null
        targetTestCase = null

    }

    override fun createTestStepListener(): SuuTestStepListener {
        return SyncTestStepListener(Environment(),referenceTestCase!!, targetTestCase!!)
    }

}