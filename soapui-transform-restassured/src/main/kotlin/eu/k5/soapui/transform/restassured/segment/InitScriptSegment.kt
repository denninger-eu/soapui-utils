package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.Environment

class InitScriptSegment(
    private val env: Environment,
    private val step: SuuTestStepScript
) : Segment {

    override val imports: List<String>
        get() = listOf(env.scriptContextFqn)


    override fun write(writer: ModelWriter): ModelWriter {






        return writer
    }


}