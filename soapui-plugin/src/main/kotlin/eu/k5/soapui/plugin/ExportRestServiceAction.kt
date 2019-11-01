package eu.k5.soapui.plugin

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction

@ActionConfiguration(
    actionGroup = "RestServiceActions", //
    toolbarPosition = ToolbarPosition.NONE, //
    toolbarIcon = "/favicon.png", //
    description = "Quick Repair Tool"
)//
class ExportRestServiceAction : AbstractSoapUIAction<RestService>("Synchronize", "Synchronize with Folder") {


    override fun perform(restService: RestService, o: Any?) {
        println("synchronize")
    }

}