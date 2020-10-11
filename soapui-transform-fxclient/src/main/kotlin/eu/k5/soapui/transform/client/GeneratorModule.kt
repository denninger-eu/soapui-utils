package eu.k5.soapui.transform.client

import eu.k5.soapui.fx.SoapUiModule
import eu.k5.soapui.transform.Generator
import javafx.scene.control.Tab
import tornadofx.Component
import tornadofx.Controller
import tornadofx.find

class GeneratorModule() : Component(), SoapUiModule {

    override val name: String = "generator"

    override fun init() {
        subscribe<GenerateTestcaseEvent> {
            generate(it)
        }
    }

    private fun generate(event: GenerateTestcaseEvent) {
        println(event.generator)
        val generator = Generator.byName(event.generator)

        val transform = generator.transform(event.suuTestCase)

        val scope = ArtifactsScope()
        scope.model.artifacts.add(Artifact("main", transform.main))
        for (artifact in transform.artifacts) {
            scope.model.artifacts.add(Artifact(artifact.name, artifact.content))
        }
        val view = find<ArtifactsView>(scope)
        val tab = Tab(event.suuTestCase.name)
        tab.contentProperty().value = view.root
        event.tabConsumer(tab)
    }


}