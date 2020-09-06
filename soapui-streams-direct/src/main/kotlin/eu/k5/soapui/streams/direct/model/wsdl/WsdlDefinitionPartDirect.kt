package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.support.definition.InterfaceDefinitionPart
import eu.k5.soapui.streams.model.wsdl.SuuWsdlDefinitionPart

class WsdlDefinitionPartDirect(
    private val interfaceDefinitionPart: InterfaceDefinitionPart
) : SuuWsdlDefinitionPart {

    override val url: String
        get() = interfaceDefinitionPart.url
    override val content: String
        get() = interfaceDefinitionPart.content


}