package eu.k5.soapui.transform.client

import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.transform.Generator
import tornadofx.App

fun main(args: Array<String>) {
    tornadofx.launch<ArtifactsStarter>(*args)
}

class ArtifactsStarter : App(ArtifactsView::class) {

    val model: ArtifactsModel by inject()

    init {

        val generator = Generator.byName("karate")

        val testCase = AbstractTest.loadFromBox("runnable2").testSuites[0].testCases[0]

        val result = generator.transform(testCase)

        model.artifacts.add(Artifact("main", result.main))
        for (artifact in result.artifacts) {
            model.artifacts.add(Artifact(artifact.name, artifact.content))
        }

/*
            this.model.artifacts.add(Artifact("test", "test"))
        this.model.artifacts.add(Artifact("test", "test"))
        this.model.artifacts.add(Artifact("test", "test"))
*/

    }
}
