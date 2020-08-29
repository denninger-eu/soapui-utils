package eu.k5.soapui.transform.restassured

import eu.k5.soapui.streams.model.test.SuuTestStepScript
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Statement
import eu.k5.soapui.transform.restassured.ast.expression.MethodCall
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral
import eu.k5.soapui.transform.restassured.model.Environment
import eu.k5.soapui.transform.restassured.model.Scenario
import eu.k5.soapui.transform.restassured.segment.ScriptSegment

class ScriptTransformer(
    private val environment: Environment,
    private val scenario: Scenario
) : Transformer<SuuTestStepScript> {


    override fun transform(step: SuuTestStepScript) {
        val fieldName = BaseTransformer.escapeVariableName(step.name)

        //writer.addArtifact(step.name + ".groovy", step.script ?: "");
        val initStatement = Statement(
            MethodCall(environment.context, Method.named("groovyScript"), listOf(StringLiteral(step.name)))
                .chain(
                    "script",
                    MethodCall(
                        environment.context,
                        Method.named("read"),
                        listOf(StringLiteral(step.name + ".groovy"))
                    )
                )
        )


        val method = Method(fieldName, ScriptSegment(environment, step))
        method.annotations.add(environment.test)
        method.annotations.add(environment.displayName(step.name))
        if (environment.lastStep != null) {
            method.annotations.add(environment.dependsOn(environment.lastStep!!))
        }
        scenario.addMethod(method)

/*        val body = InitScriptSegment(environment, step)
        for (property in step.properties.properties) {
            body.addProperty(property.name, property.value)
        }
        val method = Method("init" + fieldName, body, Visibility.PRIVATE)
        scenario.addMethod(method)*/
        scenario.init.add(initStatement)


        environment.lastStep = fieldName
    }

}