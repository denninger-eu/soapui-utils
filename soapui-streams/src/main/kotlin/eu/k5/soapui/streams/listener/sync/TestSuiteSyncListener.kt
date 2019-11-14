package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.streams.model.test.SuuTestStepListener

class TestSuiteSyncListener(
    private val environment: Environment,
    private val referenceProject: SuProject,
    private val targetProject: SuProject?
) : SuuTestSuiteListener {

    private var referenceTestSuite: SuuTestSuite? = null

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
    }

    override fun enterTestCase(targetTestCase: SuuTestCase): VisitResult {
        val ref = referenceTestSuite!!.getTestCase(targetTestCase.name)
        if (ref == null) {
            return VisitResult.TERMINATE
        }
        targetTestCase.enabled = ref.enabled
        return VisitResult.CONTINUE
    }

    override fun exitTestCase(testCase: SuuTestCase) {
    }

    override fun createTestStepListener(): SuuTestStepListener {
        return SuuTestStepListener.NO_OP
    }

}