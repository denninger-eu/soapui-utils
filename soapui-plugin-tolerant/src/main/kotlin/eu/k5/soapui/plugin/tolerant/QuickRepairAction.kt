package eu.k5.tolerant.soapui.plugin.eu.k5.soapui.plugin.tolerant

import com.eviware.soapui.impl.wsdl.WsdlRequest
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction


@ActionConfiguration(
    actionGroup = "WsdlRequestActions", //
    toolbarPosition = ToolbarPosition.NONE, //
    toolbarIcon = "/favicon.png", //
    description = "Quick Repair Tool"
)//
class QuickRepairAction : AbstractSoapUIAction<WsdlRequest>("Quick Repair", "Repairs Request") {


    override fun perform(request: WsdlRequest, o: Any?) {
        try {
          //  val tolerantConverter =
            //createTolerantConverter(request.operation.`interface`)

/*
            val converterRequest = TolerantConverterRequest()
            converterRequest.content = request.requestContent
            val result = tolerantConverter.convert(converterRequest)

            request.requestContent = result.content
*/
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        System.out.println("repair");

    }

}