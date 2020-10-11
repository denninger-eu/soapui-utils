package eu.k5.soapui.fx

import javafx.scene.Parent
import tornadofx.View
import tornadofx.borderpane

class SuuStudioView : View() {

    override val root = borderpane {
        left<ProjectView>()
        center<SuuTabs>()
    }

    init {
        this.primaryStage.minHeight = 800.0
        this.primaryStage.minWidth = 800.0

    }
}