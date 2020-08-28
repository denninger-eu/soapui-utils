package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.transform.restassured.model.Environment
import eu.k5.soapui.transform.restassured.segment.PropertyTransfersMethod
import eu.k5.soapui.transform.restassured.model.Scenario
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Visibility

class PropertyTransferTransformer(
    private val environment: Environment,
    private val scenario: Scenario
) : Transformer<SuuTestStepPropertyTransfers> {


    override fun transform(step: SuuTestStepPropertyTransfers) {
        val body = PropertyTransfersMethod(step.name)
        for (transfer in step.transfers) {
            body.addTransfer(transfer)
        }
        val method = Method(step.name, body, Visibility.PUBLIC)
        method.annotations.add(environment.test)
        method.annotations.add(environment.displayName(step.name))
        method.annotations.add(environment.dependsOn(step.name))
        scenario.addMethod(method)
    }

}