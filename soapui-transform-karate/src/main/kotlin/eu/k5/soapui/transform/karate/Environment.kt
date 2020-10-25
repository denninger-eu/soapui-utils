package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.TransformationResult
import eu.k5.soapui.transform.karate.model.Scenario
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral
import java.io.StringWriter

class Environment {

    private var featureVariable: Int = 0
    val artifacts = HashMap<String, Artifact>()
    private val steps = HashMap<String, VariableLiteral>()

    var mainDocument: String? = null
    var mainName: String = "main.feature"

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


    fun write(scenario: Scenario) {
        val writer = ModelWriter()
        mainName = base.escapeVariableName(scenario.description) + ".feature"
        scenario.write(writer)
        mainDocument = writer.mainContent()
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