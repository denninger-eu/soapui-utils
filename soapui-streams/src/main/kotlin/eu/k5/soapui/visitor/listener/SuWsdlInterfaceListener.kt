package eu.k5.soapui.visitor.listener


interface SuWsdlInterfaceListener {
    fun unsupportedInterface(env: Environment, interfaze: SuInterface) {

    }

    fun enterInterface(env: Environment, wsdlInterface: SuWsdlInterface)
    fun exitInterface(env: Environment, wsdlInterface: SuWsdlInterface)

    fun unsupportedOperation(env: Environment, operation: SuOperation) {

    }

    fun enterOperation(env: Environment, operation1: SuWsdlOperation)
    fun exitOperation(env: Environment, operation: SuWsdlOperation)

    fun request(env: Environment, wsdlRequest: SuWsdlRequest)

    companion object {
        val NO_OP = object : SuWsdlInterfaceListener {
            override fun enterInterface(env: Environment, wsdlInterface: SuWsdlInterface) {
            }

            override fun exitInterface(env: Environment, wsdlInterface: SuWsdlInterface) {
            }

            override fun enterOperation(env: Environment, operation1: SuWsdlOperation) {
            }

            override fun exitOperation(env: Environment, operation: SuWsdlOperation) {
            }

            override fun request(env: Environment, wsdlRequest: SuWsdlRequest) {
            }

        }
    }
}