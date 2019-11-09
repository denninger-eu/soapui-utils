package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.WsdlProject
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.lang.UnsupportedOperationException
import java.nio.file.Path

class ProjectDirect(
    private val wsdlProject: WsdlProject
) : SuProject {

    override var name: String
        get() = wsdlProject.name ?: ""
        set(value) {
            wsdlProject.name = value
        }

    override var description: String?
        get() = wsdlProject.description
        set(value) {
            wsdlProject.description = value
        }

    override val restServices: List<SuuRestService>
        get() = wsdlProject.interfaceList.filterIsInstance<RestService>().map { RestServiceDirect(it) }

    override fun createRestService(name: String): SuuRestService {
        val newRestService = wsdlProject.addNewInterface(name, "rest") as RestService
        return RestServiceDirect(newRestService)
    }

    fun save(path: Path) {
        wsdlProject.saveIn(path.toFile())
    }
}