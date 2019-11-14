package eu.k5.soapui.streams

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.test.*

fun SuProject.apply(listener: SuListener): SuProject {
    listener.enterProject(Environment(), this)
    for (restService in this.restServices) {
        restService.apply(listener.createResourceListener())
    }
    val testSuiteListener = listener.createTestSuiteListener()
    for (testSuite in this.testSuites) {
        testSuite.apply(testSuiteListener)
    }
    listener.exitProject(this)
    return this
}

fun SuuRestService.apply(listener: SuuRestServiceListener) {
    val result = listener.enter(this)
    if (result == VisitResult.TERMINATE) {
        return
    }
    for (resource in this.resources) {
        resource.apply(listener)
    }
    listener.exit(this)
}

fun SuuRestResource.apply(listener: SuuRestServiceListener) {
    val result = listener.enterResource(this)
    if (result == VisitResult.TERMINATE) {
        return
    }
    for (method in this.methods) {
        method.apply(listener)
    }
    for (resource in this.childResources) {
        resource.apply(listener)
    }
    listener.exitResource(this)
}

fun SuuRestMethod.apply(listener: SuuRestServiceListener) {
    val result = listener.enterMethod(this)
    if (result == VisitResult.TERMINATE) {
        return
    }
    for (request in this.requests) {
        listener.handleRequest(request)
    }
    listener.exitMethod(this)
}

fun SuuTestSuite.apply(listener: SuuTestSuiteListener) {
    val result = listener.enter(this)
    if (result == VisitResult.TERMINATE) {
        return
    }
    for (testCase in this.testCases) {
        testCase.apply(listener)
    }
    listener.exit(this)
}

fun SuuTestCase.apply(listener: SuuTestSuiteListener) {
    val result = listener.enterTestCase(this)
    if (result == VisitResult.TERMINATE) {
        return
    }

    val testStepListener = listener.createTestStepListener()
    for (step in this.steps) {
        if (step is SuuTestStepPropertyTransfers) {
            testStepListener.transfer(step)
        } else if (step is SuuTestStepDelay){
            testStepListener.delay(step)
        } else if (step is SuuTestStepRestRequest){
            testStepListener.restRequest(step)
        }
    }

    listener.exitTestCase(this)
}