package eu.k5.soapui.visitor.listener


interface SuTestSuiteListener {

    fun unsupportedTestSuite(env: Environment, suite: SuTestSuite) {

    }

    fun enterTestSuite(env: Environment, suite: SuWsdlTestSuite)
    fun exitTestSuite(env: Environment, suite: SuWsdlTestSuite)

    fun unsupportedTestCase(env: Environment, testCase: SuTestCase) {

    }

    fun enterTestCase(env: Environment, testCase: SuWsdlTestCase)
    fun exitTestCase(env: Environment, testCase: SuWsdlTestCase)

    fun createTestStepListener(): SuTestStepListener
}