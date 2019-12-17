package eu.k5.soapui.plugin.imex

import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.project.Project
import eu.k5.soapui.plugin.SuuConfig
import eu.k5.soapui.streams.listener.difference.DiffEntry
import eu.k5.soapui.streams.listener.difference.Differences
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService

class ImexModel(
    val project: WsdlProject,
    val config: SuuConfig = loadDefaultConfig()
) {

    var folder: Observable<String?> = Observable()
    var restService: SuuRestService? = null

    var target: Observable<SuProject?> = Observable()

    var differences: Observable<Differences?> = Observable()

    val createEnabled = Observable<Boolean?>()

    val exportEnabled = Observable<Boolean?>()

    val importEnabled = Observable<Boolean?>()

    enum class Mode {
        IMPORT, EXPORT
    }

    companion object {
        fun loadDefaultConfig(): SuuConfig {
            return SuuConfig.loadDefault()
        }
    }

}