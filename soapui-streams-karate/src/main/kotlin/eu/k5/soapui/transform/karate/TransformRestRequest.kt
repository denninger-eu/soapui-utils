package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import eu.k5.soapui.transform.karate.model.NoOpStatement
import eu.k5.soapui.transform.karate.model.Statement

class TransformRestRequest : Transformer<SuuTestStepRestRequest> {

    override fun header(step: SuuTestStepRestRequest): Statement {
        return NoOpStatement()
    }

    override fun body(step: SuuTestStepRestRequest): Statement {
        return NoOpStatement()
    }

}