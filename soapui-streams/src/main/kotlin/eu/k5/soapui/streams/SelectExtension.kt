package eu.k5.soapui.streams

import com.sun.org.apache.bcel.internal.generic.Select
import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.assertion.*
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.*
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener

fun SuProject.select(select: (Any) -> Boolean): MutableList<Any> {
    val query = SelectionQuery(select)

    val select = SelectListener(query)
    this.apply(select)

    return query.results
}


private class SelectionQuery(
    val matches: (Any) -> Boolean,
    val results: MutableList<Any> = ArrayList()
) {
    fun tryAdd(obj: Any) {
        if (matches(obj)) {
            results.add(obj)
        }
    }
}

private class SelectListener(
    private val query: SelectionQuery
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
        return SelectTestSuiteListener(query)
    }

}

private class SelectTestSuiteListener(
    private val query: SelectionQuery
) : SuuTestSuiteListener {
    override fun enter(suite: SuuTestSuite): VisitResult {
        return VisitResult.CONTINUE
    }

    override fun exit(suite: SuuTestSuite) {
    }

    override fun enterTestCase(testCase: SuuTestCase): VisitResult {
        return VisitResult.CONTINUE

    }

    override fun exitTestCase(testCase: SuuTestCase) {
    }

    override fun createTestStepListener(): SuuTestStepListener {
        return SelectTestStepListener(query)
    }

}

private class SelectTestStepListener(
    val query: SelectionQuery
) : SuuTestStepListener {


    override fun script(step: SuuTestStepScript) {
    }

    override fun transfer(step: SuuTestStepPropertyTransfers) {
    }

    override fun enterRestRequest(step: SuuTestStepRestRequest): VisitResult {
        return VisitResult.CONTINUE
    }

    override fun exitRestRequest(step: SuuTestStepRestRequest) {
    }

    override fun enterWsdlRequest(step: SuuTestStepWsdlRequest): VisitResult {
        return VisitResult.CONTINUE
    }

    override fun exitWsdlRequest(step: SuuTestStepWsdlRequest) {
    }

    override fun properties(step: SuuTestStepProperties) {
    }

    override fun delay(step: SuuTestStepDelay) {
    }

    override fun createAssertionListener(): SuuAssertionListener {
        return SelectAssertionListener(query)
    }

}


private class SelectAssertionListener(
    val query: SelectionQuery
) : SuuAssertionListener {
    override fun soapResponse(assertion: SuuAssertionSoapResponse) {
        query.tryAdd(assertion)
    }

    override fun validStatus(assertion: SuuAssertionValidStatus) {
        query.tryAdd(assertion)
    }

    override fun invalidStatus(assertion: SuuAssertionInvalidStatus) {
        query.tryAdd(assertion)
    }

    override fun contains(assertion: SuuAssertionContains) {
        query.tryAdd(assertion)
    }

    override fun notContains(assertion: SuuAssertionNotContains) {
        query.tryAdd(assertion)
    }

    override fun script(assertion: SuuAssertionScript) {
        query.tryAdd(assertion)
    }

    override fun duration(assertion: SuuAssertionDuration) {
        query.tryAdd(assertion)
    }

    override fun jsonPathCount(assertion: SuuAssertionJsonPathCount) {
        query.tryAdd(assertion)
    }

    override fun jsonPathExits(assertion: SuuAssertionJsonPathExists) {
        query.tryAdd(assertion)
    }

    override fun jsonPathMatch(assertion: SuuAssertionJsonPathMatch) {
        query.tryAdd(assertion)
    }

    override fun jsonPathRegEx(assertion: SuuAssertionJsonPathRegEx) {
        query.tryAdd(assertion)
    }

    override fun xpath(assertion: SuuAssertionXPath) {
        query.tryAdd(assertion)
    }

    override fun xquery(assertion: SuuAssertionXQuery) {
        query.tryAdd(assertion)
    }

    override fun exitAssertions(assertions: SuuAssertions) {
    }

}