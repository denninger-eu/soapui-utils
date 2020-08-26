package eu.k5.soapui.transform

class TransformationResult(
    val main: String
) {
    val artifacts: MutableList<Artifact> = ArrayList()

    class Artifact(
        val name: String,
        val content: String
    )
}