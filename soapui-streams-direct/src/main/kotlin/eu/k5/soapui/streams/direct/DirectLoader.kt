package eu.k5.soapui.streams.direct

import com.eviware.soapui.impl.wsdl.WsdlProject
import eu.k5.soapui.streams.SuLoader
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuListener
import java.io.InputStream
import java.io.Reader

class DirectLoader : SuLoader {

    fun newProject(): ProjectDirect {
        return ProjectDirect(WsdlProject())
    }

    fun direct(inputStream: InputStream): ProjectDirect {
        val wsdlProject = WsdlProject(inputStream, null)
        return ProjectDirect(wsdlProject)
    }

    override fun getName(): String = "direct"

    override fun load(inputStream: InputStream): SuProject {
        return direct(inputStream)
    }

/*    override fun bind(inputStream: InputStream): SuProject {
        val wsdlProject = WsdlProject(inputStream, null)

        return ProjectDirect(wsdlProject)

        TODO("maybe deprecated")
*//*        val project = Project()
        val sync = SyncListener(ProjectDirect(wsdlProject))
        project.apply(sync)
        return project*//*

*//*
        val parser = DirectParser(project, Environment())

        val listener = DirectBindListener()
        parser.parse(listener)

        println(listener.project)

        println(listener.project!!.toXml())

        return listener.project!!*//*
    }*/


}