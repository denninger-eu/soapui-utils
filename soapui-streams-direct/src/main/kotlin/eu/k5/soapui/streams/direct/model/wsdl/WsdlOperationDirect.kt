package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.wsdl.WsdlOperation
import com.eviware.soapui.impl.wsdl.WsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class WsdlOperationDirect(
    private val operation: WsdlOperation
) : SuuWsdlOperation {
    override val name: String
        get() = operation.name

    override var description: String?
        get() = operation.description
        set(value) {
            operation.description = value
        }
    override val requests: List<SuuWsdlRequest>
        get() = operation.requestList.filterIsInstance<WsdlRequest>().map { WsdlRequestDirect(it) }
}