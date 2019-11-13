package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.visitor.listener.SuTestStepListener

class DifferenceTestSuiteListener(
    private val differences: Differences,
    private val referenceProject: SuProject

) : SuuTestSuiteListener {

    private var referenceTestSuite: SuuTestSuite? = null

    override fun enter(suite: SuuTestSuite): VisitResult {

        val ref = referenceProject.getTestSuite(suite.name)

        if (ref == null) {
            differences.addMissing(Differences.Type.TEST_SUITE, suite.name)
            return VisitResult.TERMINATE
        }
        differences.push(Differences.Type.TEST_SUITE, suite.name)

        differences.addChange("enabled", ref.enabled, suite.enabled)

        DifferenceListener.handleProperties(differences, ref.properties, suite.properties)

        referenceTestSuite = ref
        return VisitResult.CONTINUE
    }

    override fun exit(suite: SuuTestSuite) {
    }

    override fun enterTestCase(testCase: SuuTestCase): VisitResult {
        val ref = referenceTestSuite!!.getTestCase(testCase.name)
        if (ref == null) {
            differences.addMissing(Differences.Type.TEST_CASE, testCase.name)
            return VisitResult.TERMINATE
        }
        differences.push(Differences.Type.TEST_CASE, testCase.name)
        differences.addChange("enabled", ref.enabled, testCase.enabled)
        DifferenceListener.handleProperties(differences, ref.properties, testCase.properties)


        return VisitResult.TERMINATE
    }

    override fun exitTestCase(testCase: SuuTestCase) {
    }

    override fun createTestStepListener(): SuTestStepListener {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}