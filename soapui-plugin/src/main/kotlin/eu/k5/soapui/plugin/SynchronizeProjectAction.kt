package eu.k5.soapui.plugin

import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction

@ActionConfiguration(
    actionGroup = "EnabledWsdlProjectActions", //
    toolbarPosition = ToolbarPosition.NONE, //
    toolbarIcon = "/favicon.png", //
    description = "Quick Repair Tool",
    targetType = WsdlProject::class
)// https://support.smartbear.com/readyapi/docs/configure/plugins/dev/annotations/actions/action.html
class SynchronizeProjectAction : AbstractSoapUIAction<WsdlProject>("Synchronize", "Synchronize with folder") {


    override fun perform(project: WsdlProject, o: Any?) {
        println("do synchronize")
/*
        try {

            val model = ImexModel(project as WsdlProject, config = SuuConfig.loadDefault())

            val view = ImexView(model)
            view.display()

        } catch (exception: Throwable) {
            exception.printStackTrace(System.err)
            throw exception
        }*/

    }

}