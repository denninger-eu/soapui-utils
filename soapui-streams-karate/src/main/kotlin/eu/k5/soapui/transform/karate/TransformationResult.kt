package eu.k5.soapui.transform.karate

class TransformationResult(
    val main: String
) {


    val artefacts: Map<String, Artefact> = HashMap()

    class Artefact(
        val name: String
    ) {

    }
}