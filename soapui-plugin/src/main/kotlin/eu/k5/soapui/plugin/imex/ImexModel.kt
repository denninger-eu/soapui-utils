package eu.k5.soapui.plugin.imex

import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.project.Project
import eu.k5.soapui.plugin.SuuConfig
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService

class ImexModel(
    val project: WsdlProject,
    val config: SuuConfig = loadDefaultConfig()
) {

    var folder: String? = null

    var restService: SuuRestService? = null



    enum class Mode {
        IMPORT, EXPORT
    }

    companion object {
        fun loadDefaultConfig(): SuuConfig {
            return SuuConfig.loadDefault()
        }
    }

}