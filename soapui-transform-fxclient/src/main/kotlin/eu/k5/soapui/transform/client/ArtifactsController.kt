package eu.k5.soapui.transform.client

import tornadofx.Controller

fun main(args: Array<String>) {
    println("testxx".matches(Regex("test.*")))
    println("test\n\r".matches(Regex("test.*",RegexOption.MULTILINE)))
    println("test\n".matches(Regex("test.*",RegexOption.DOT_MATCHES_ALL)))

}

class ArtifactsController : Controller() {


    val selectedArtifact = ArtifactsModel()

    fun saveAsZip() {
        "".matches(Regex("22", RegexOption.MULTILINE))
        TODO("Not yet implemented")
    }

    fun saveInFolder() {
        TODO("Not yet implemented")
    }


}