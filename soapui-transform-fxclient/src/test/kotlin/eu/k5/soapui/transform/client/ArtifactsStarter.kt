package eu.k5.soapui.transform.client

import eu.k5.soapui.test.AbstractTest
import eu.k5.soapui.transform.Generator
import tornadofx.App
import tornadofx.find

fun main(args: Array<String>) {
    tornadofx.launch<ArtifactsStarter>(*args)
}

class ArtifactsStarter : App(ArtifactStarterView::class) {

    val view: ArtifactStarterView by inject()

    init {


        val generator = Generator.byName("karate")


        val testCase = AbstractTest.loadFromBox("runnable2").testSuites[0].testCases[0]

        val transform = generator.transform(testCase)
        val scope = ArtifactsScope()
        scope.model.artifacts.add(Artifact("main", transform.getMainDocument()))
        for (artifact in transform.artifacts) {
            scope.model.artifacts.add(Artifact(artifact.name, artifact.content))
        }
        val artifactsView = find<ArtifactsView>(scope)

        view.root.center = artifactsView.root

/*
        model.artifacts.add(Artifact("main", result.main))
        for (artifact in result.artifacts) {
            model.artifacts.add(Artifact(artifact.name, artifact.content))
        }
*/

/*
            this.model.artifacts.add(Artifact("test", "test"))
        this.model.artifacts.add(Artifact("test", "test"))
        this.model.artifacts.add(Artifact("test", "test"))
*/

    }
}
