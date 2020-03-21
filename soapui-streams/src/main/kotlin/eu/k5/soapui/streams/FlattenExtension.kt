package eu.k5.soapui.streams

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener

fun SuProject.flatten(): List<Any> {
    val list = ArrayList<Any>()
    val select = FlattenListener(list)
    this.apply(select)
    return list
}


private class FlattenListener(
    private val list: MutableList<Any>
) : SuListener {
    override fun createWsdlServiceListener(): SuuWsdlServiceListener {
        return SuuWsdlServiceListener.NO_OP
    }

    private var project: SuProject? = null
    override fun enterProject(env: Environment, project: SuProject) {
        this.project = project
    }

    override fun exitProject(suuProject: SuProject) {
    }

    override fun createRestServiceListener(): SuuRestServiceListener {
        return SuuRestServiceListener.NO_OP
    }

    override fun createTestSuiteListener(): SuuTestSuiteListener {
        return FlattenTestSuiteListener(list)
    }

}

private class FlattenTestSuiteListener(
    private val list: MutableList<Any>
) : SuuTestSuiteListener {
    override fun enter(suite: SuuTestSuite): VisitResult {
        list.add(suite)
        return VisitResult.CONTINUE
    }

    override fun exit(suite: SuuTestSuite) {
    }

    override fun enterTestCase(testCase: SuuTestCase): VisitResult {
        list.add(testCase)
        return VisitResult.CONTINUE
    }

    override fun exitTestCase(testCase: SuuTestCase) {
    }

    override fun createTestStepListener(): SuuTestStepListener {
        return FlattenTestStepListener(list)
    }

}

private class FlattenTestStepListener(
    private val list: MutableList<Any>
) : SuuTestStepListener {


    override fun script(step: SuuTestStepScript) {
        list.add(step)
    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {
        list.add(step)
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        list.add(step)
        return VisitResult.CONTINUE
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
    }


    override fun enterWsdlRequest(step: SuuTestStepWsdlRequest): VisitResult {
        list.add(step)
        return VisitResult.CONTINUE
    }

    override fun exitWsdlRequest(step: SuuTestStepWsdlRequest) {
    }

    override fun properties(step: SuuTestStepProperties) {
        list.add(step)
    }

    override fun delay(step: SuuTestStepDelay) {
        list.add(step)
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return FlattenAssertionListener(list)
    }

}


private class FlattenAssertionListener(
    private val list: MutableList<Any>
) : SuuAssertionListener {
    override fun soapResponse(assertion: SuuAssertionSoapResponse) {
        list.add(assertion)
    }

    override fun validStatus(assertion: SuuAssertionValidStatus) {
        list.add(assertion)
    }

    override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
        list.add(assertion)
    }

    override fun contains(assertion: SuuAssertionContains) {
        list.add(assertion)
    }

    override fun notContains(assertion: SuuAssertionNotContains) {
        list.add(assertion)
    }

    override fun script(assertion: SuuAssertionScript) {
        list.add(assertion)
    }

    override fun duration(assertion: SuuAssertionDuration) {
        list.add(assertion)
    }

    override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
        list.add(assertion)
    }

    override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
        list.add(assertion)
    }

    override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
        list.add(assertion)
    }

    override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
        list.add(assertion)
    }

    override fun xpath(assertion: SuuAssertionXPath) {
        list.add(assertion)
    }

    override fun xquery(assertion: SuuAssertionXQuery) {
        list.add(assertion)
    }

    override fun exitAssertions(assertions: SuuAssertions) {
    }

}