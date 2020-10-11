package eu.k5.soapui.fx

import javafx.geometry.Orientation
import javafx.scene.Parent
import tornadofx.View
import tornadofx.borderpane
import tornadofx.splitpane

class SuuStudioView : View() {

    override val root = splitpane(Orientation.HORIZONTAL)
    {
        add(ProjectView::class)
        add(SuuTabs::class)
        setDividerPositions(0.0, 100.0)
    }

    init {
        this.primaryStage.minHeight = 800.0
        this.primaryStage.minWidth = 1200.0
    }
}