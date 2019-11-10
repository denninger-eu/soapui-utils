package eu.k5.soapui.plugin

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.box.ProjectBox
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.direct.model.RestServiceDirect
import eu.k5.soapui.streams.listener.resource.DirectSyncListener
import eu.k5.soapui.streams.listener.resource.DirectSyncResourceListener
import eu.k5.soapui.visitor.listener.Environment
import java.nio.file.Paths

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

            val path = Paths.get("c:", "data", "Transient", "project")


            val project = ProjectBox.load(path)


            Suu.syncRestService(ProjectDirect(restService.project), project, restService.name)
/*
            val listener =
                DirectSyncResourceListener(Environment(), ProjectDirect(restService.project), project)

            RestServiceDirect(restService).apply(listener)
*/

        } catch (exception: Throwable) {
            exception.printStackTrace(System.err)
            throw exception
        }

    }

}