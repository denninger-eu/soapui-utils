package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.transform.restassured.segment.PropertyTransfersMethod
import eu.k5.soapui.transform.restassured.segment.Scenario
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Visibility

class PropertyTransferTransformer(
    private val environment: Environment,
    private val scenario: Scenario
) : Transformer<SuuTestStepPropertyTransfers> {


    override fun transform(step: SuuTestStepPropertyTransfers) {
        val fieldName = BaseTransformer.escapeVariableName(step.name)

        val body = PropertyTransfersMethod(step.name)
        for (transfer in step.transfers) {
            body.addTransfer(transfer)
        }
        val method = Method(fieldName, body, Visibility.PUBLIC)
        method.annotations.add(environment.test)
        method.annotations.add(environment.displayName(step.name))
        if (environment.lastStep != null) {
            method.annotations.add(environment.dependsOn(environment.lastStep!!))
        }
        scenario.addMethod(method)

        environment.lastStep = fieldName

    }

}