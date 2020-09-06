package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlContext
import eu.k5.soapui.streams.model.wsdl.SuuWsdlDefinition
import eu.k5.soapui.streams.model.wsdl.SuuWsdlDefinitionPart

class WsdlDefinitionDirect(
    private val definitionContext: WsdlContext
) : SuuWsdlDefinition {
    override val parts: List<SuuWsdlDefinitionPart>
        get() = definitionContext.definitionParts.map { WsdlDefinitionPartDirect(it) }

}