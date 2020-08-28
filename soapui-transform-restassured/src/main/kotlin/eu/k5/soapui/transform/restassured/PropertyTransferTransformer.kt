package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.transform.restassured.model.Environment
import eu.k5.soapui.transform.restassured.model.PropertiesMethod
import eu.k5.soapui.transform.restassured.model.PropertyTransfersMethod
import eu.k5.soapui.transform.restassured.model.Scenario
import eu.k5.soapui.transform.restassured.model.base.Method
import eu.k5.soapui.transform.restassured.model.base.MethodCall
import eu.k5.soapui.transform.restassured.model.base.Statement
import eu.k5.soapui.transform.restassured.model.base.Visibility

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
        scenario.methods.add(method)
    }

}