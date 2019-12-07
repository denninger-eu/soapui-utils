package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.wsdl.WsdlOperation
import com.eviware.soapui.impl.wsdl.WsdlRequest
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class WsdlOperationDirect(
    private val operation: WsdlOperation
) : SuuWsdlOperation {
    override fun markLostAndFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRequest(name: String): SuuWsdlRequest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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