package eu.k5.soapui.transform

class TransformationResult(
    val project: String,
    val testSuite: String,
    val testCase: String,
    val mainName: String,
) {
    val artifacts: MutableList<Artifact> = ArrayList()

    class Artifact(
        val name: String,
        val type: ArtifactType,
        val content: String
    )

    fun getMainDocument(): String = artifacts.firstOrNull { it.name == mainName }?.content ?: ""


    enum class ArtifactType {
        JAVA, KARATE, XML, GROOVY, JSON, TEXT
    }
}