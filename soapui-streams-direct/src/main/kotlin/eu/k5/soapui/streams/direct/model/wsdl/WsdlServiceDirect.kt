package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService

class WsdlServiceDirect(
    private val wsdlInterface: WsdlInterface
) : SuuWsdlService {

    override var name: String
        get() = wsdlInterface.name
        set(value) {
            wsdlInterface.name = value
        }

    override var description: String?
        get() = wsdlInterface.description
        set(value) {
            wsdlInterface.description = value
        }

    override val operations: List<SuuWsdlOperation>
        get() = wsdlInterface.operationList.filterIsInstance<WsdlOperation>().map { WsdlOperationDirect(it) }

}