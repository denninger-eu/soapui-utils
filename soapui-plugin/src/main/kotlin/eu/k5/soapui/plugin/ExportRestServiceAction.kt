package eu.k5.soapui.plugin

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.direct.model.RestServiceDirect
import eu.k5.soapui.streams.listener.resource.DirectBindListener
import eu.k5.soapui.streams.listener.resource.DirectBindRestServiceListener
import eu.k5.soapui.streams.listener.resource.DirectSyncListener
import eu.k5.soapui.streams.listener.resource.DirectSyncResourceListener
import eu.k5.soapui.streams.jaxb.Project

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

            restService.project.getProperty("")

            val restService = RestServiceDirect(restService)

            val directSyncListener = DirectSyncListener(Project("dummy"))


            restService.apply(directSyncListener.createResourceListener())

            println("target: " + directSyncListener.project.toXml())
        } catch (exception: Throwable) {
            exception.printStackTrace(System.err)
            throw exception
        }

    }

}