package eu.k5.soapui.fx

import eu.k5.soapui.transform.Generator
import javafx.scene.Parent
import javafx.scene.control.TabPane
import tornadofx.View
import tornadofx.label
import tornadofx.singleAssign
import tornadofx.tabpane

class SuuTabs : View() {

    init {
        subscribe<GenerateTestcaseEvent>() {

            val generator = Generator.byName(it.generator)
            generator.transform(it.suuTestCase)


        }
    }

    override val root = tabpane {
        tab<WelcomeView>() {
            title = "welcome"
        }
    }

}