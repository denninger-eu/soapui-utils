package eu.k5.soapui.plugin

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import eu.k5.soapui.plugin.imex.ImexModel
import eu.k5.soapui.plugin.imex.ImexView
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.direct.model.rest.RestServiceDirect

@ActionConfiguration(
    actionGroup = "ProjectActions", //
    toolbarPosition = ToolbarPosition.NONE, //
    toolbarIcon = "/favicon.png", //
    description = "Quick Repair Tool"
)//
class SynchronizeProjectAction : AbstractSoapUIAction<WsdlProject>("Synchronize", "Export to Folder") {


    override fun perform(project: WsdlProject, o: Any?) {
        println("do synchronize")

        try {

            val model = ImexModel(project, config = SuuConfig.loadDefault())

            val view = ImexView(model)
            view.display()

        } catch (exception: Throwable) {
            exception.printStackTrace(System.err)
            throw exception
        }

    }

}