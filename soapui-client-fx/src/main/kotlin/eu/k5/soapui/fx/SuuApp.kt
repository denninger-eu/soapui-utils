package eu.k5.soapui.fx

import tornadofx.App
import java.util.*

class SuuApp : App(SuuStudioView::class) {
    init {
        val loader = ServiceLoader.load(SoapUiModule::class.java)

        for (module in loader) {
            println("Init " + module.name)
            module.init()
            println("initialized " + module.name)
        }
    }
}
