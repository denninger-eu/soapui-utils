package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlOperation
import com.ibm.wsdl.BindingOperationImpl
import com.ibm.wsdl.OperationImpl
import eu.k5.soapui.streams.model.wsdl.SuuWsdlDefinition
import eu.k5.soapui.streams.model.wsdl.SuuWsdlOperation
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import javax.wsdl.Operation

class WsdlServiceDirect(
    private val wsdlInterface: WsdlInterface
) : SuuWsdlService {
    override fun markLostAndFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createOperation(name: String): SuuWsdlOperation {
        val bindingOperation = BindingOperationImpl()
        bindingOperation.name = name
        val operation : Operation =  OperationImpl()
        operation.name = name
        bindingOperation.operation = operation
        val newOperation = wsdlInterface.addNewOperation(bindingOperation)
        return WsdlOperationDirect(newOperation)
    }

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


    override val definition: SuuWsdlDefinition
        get() = WsdlDefinitionDirect(wsdlInterface.definitionContext)
}