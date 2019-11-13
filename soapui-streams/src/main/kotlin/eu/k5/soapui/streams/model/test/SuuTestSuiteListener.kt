package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.visitor.listener.*


interface SuuTestSuiteListener {

    fun unsupportedTestSuite(env: Environment, suite: SuTestSuite) {

    }

    fun enter(suite: SuuTestSuite): VisitResult
    fun exit(suite: SuuTestSuite)

    fun enterTestCase(testCase: SuuTestCase): VisitResult
    fun exitTestCase(testCase: SuuTestCase)


    fun unsupportedTestCase(env: Environment, testCase: SuTestCase) {

    }


    fun createTestStepListener(): SuTestStepListener

    companion object {
        val NO_OP = object : SuuTestSuiteListener {
            override fun enter(suite: SuuTestSuite): VisitResult {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun exit(suite: SuuTestSuite) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun enterTestCase(testCase: SuuTestCase): VisitResult {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun exitTestCase(testCase: SuuTestCase) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createTestStepListener(): SuTestStepListener {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
}