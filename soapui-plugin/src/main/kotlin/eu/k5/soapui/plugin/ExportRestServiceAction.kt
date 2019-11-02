package eu.k5.soapui.plugin

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.direct.model.RestServiceDirect
import eu.k5.soapui.streams.listener.resource.DirectBindListener
import eu.k5.soapui.streams.listener.resource.DirectBindRestServiceListener
import eu.k5.soapui.streams.model.Project
import java.lang.Exception

@ActionConfiguration(
    actionGroup = "RestServiceActions", //
    toolbarPosition = ToolbarPosition.NONE, //
    toolbarIcon = "/favicon.png", //
    description = "Quick Repair Tool"
)//
class ExportRestServiceAction : AbstractSoapUIAction<RestService>("Synchronize", "Synchronize with Folder") {


    override fun perform(restService: RestService, o: Any?) {
        println("synchronize")

        try {
            val restService = RestServiceDirect(restService)


            val listener = DirectBindRestServiceListener(Project("dummy"))
            restService.apply(listener)

            println("target: " + listener.project.toXml())
        } catch (exception: Throwable) {
            exception.printStackTrace(System.err)
            throw exception
        }

    }

}