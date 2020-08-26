package eu.k5.soapui.transform.karate

import eu.k5.soapui.transform.TransformationResult
import eu.k5.soapui.transform.karate.model.Scenario
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import java.io.StringWriter

class Environment {

    private var featureVariable: Int = 0
    private val artifacts = HashMap<String, Artifact>()
    private val steps = HashMap<String, VariableLiteral>()

    private var mainDocument: String? = null

    val ctx: VariableLiteral = VariableLiteral("ctx")

    val base = BaseTransformer(this)

    val restRequestTransformer: TransformRestRequest by lazy { TransformRestRequest(this) }

    val scriptTransformer: TransformScript by lazy { TransformScript(this) }

    val propertiesTransformer: TransformProperties by lazy { TransformProperties(this) }

    val transferTransformer: TransformTransfers by lazy { TransformTransfers(this) }

    fun getTempFeatureVariable(): VariableLiteral {
        return VariableLiteral("t" + featureVariable++)
    }

    fun getVariableForStep(name: String): VariableLiteral {
        return steps.computeIfAbsent(name) { VariableLiteral(base.escapeVariableName(name)) }
    }

    fun addArtifact(name: String, content: String, type: String): Artifact {
        val artifact = Artifact(name + type, content, VariableLiteral(base.escapeVariableName(name)))
        artifacts[name] = artifact
        return artifact
    }

    fun getResult(): TransformationResult {
        val result = TransformationResult(mainDocument ?: "")
        for (artifact in artifacts) {
            result.artifacts.add(TransformationResult.Artifact(artifact.value.name, artifact.value.content))
        }
        return result
    }

    fun write(scenario: Scenario) {
        val writer = StringWriter()
        scenario.write(ModelWriter(writer, this))
        mainDocument = writer.toString()
    }


    val contextClassName = "eu.k5.dread.soapui.SoapuiContext"

    val createPropertiesStep = "propertiesStep"

    val createRequestStep = "requestStep"

    val expandProperties = "expand"

    val baseUrl = "\${#TestCase#baseUrl}"


    class Artifact(
        val name: String,
        val content: String,
        val variable: VariableLiteral
    )
}