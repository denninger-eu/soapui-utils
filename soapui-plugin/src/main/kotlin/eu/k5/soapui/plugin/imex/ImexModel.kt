package eu.k5.soapui.plugin.imex

import eu.k5.soapui.plugin.SuuConfig
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService

class ImexModel(
    val project: SuProject,
    val config: SuuConfig = loadDefaultConfig()
) {

    var folder: String? = null

    var restService: SuuRestService? = null

    var differences: List<String> = ArrayList()

    enum class Mode {
        IMPORT, EXPORT
    }

    companion object {
        fun loadDefaultConfig(): SuuConfig {
            return SuuConfig.loadDefault()
        }
    }

}