package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.wsdl.WsdlProject
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.lang.UnsupportedOperationException

class ProjectDirect(
    private val wsdlProject: WsdlProject
) : SuProject {
    override fun addRestService(restService: SuuRestService) = throw UnsupportedOperationException()

    override var name: String = wsdlProject.name


}