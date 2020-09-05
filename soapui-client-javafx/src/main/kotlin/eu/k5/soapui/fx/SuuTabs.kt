package eu.k5.soapui.fx

import eu.k5.soapui.transform.Generator
import eu.k5.soapui.transform.client.GenerateTestcaseEvent
import tornadofx.View
import tornadofx.tabpane

class SuuTabs : View() {

    init {
        subscribe<NewTabEvent>() {
            root.tabs.add(it.tab)
        }
    }

    override val root = tabpane {
        tab<WelcomeView>() {
            title = "welcome"
        }
    }

}