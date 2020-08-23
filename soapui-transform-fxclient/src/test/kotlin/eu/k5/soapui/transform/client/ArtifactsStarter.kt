package eu.k5.soapui.transform.client

import tornadofx.App

fun main(args: Array<String>) {
    tornadofx.launch<ArtifactsStarter>(*args)
}

class ArtifactsStarter : App(ArtifactsView::class) {

}
