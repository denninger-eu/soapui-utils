package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.WsdlProject
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.lang.UnsupportedOperationException

class ProjectDirect(
    private val wsdlProject: WsdlProject
) : SuProject {


    override fun addRestService(restService: SuuRestService) = throw UnsupportedOperationException()

    override var name: String = wsdlProject.name


    override val restServices: List<SuuRestService>
        get() = wsdlProject.interfaceList.filterIsInstance<RestService>().map { RestServiceDirect(it) }

    override fun createRestService(name: String): SuuRestService {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}