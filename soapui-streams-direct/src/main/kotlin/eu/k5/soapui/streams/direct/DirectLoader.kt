package eu.k5.soapui.streams.direct

import com.eviware.soapui.impl.wsdl.WsdlProject
import eu.k5.soapui.streams.Loader
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.model.Project
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuListener
import java.io.InputStream
import java.io.Reader
import java.io.StringWriter
import javax.xml.bind.JAXBContext

class DirectLoader : Loader {

    override fun bind(inputStream: InputStream): SuProject {
        val project = WsdlProject(inputStream, null)

        val context = JAXBContext.newInstance(Project::class.java)
        val parser = DirectParser(project, Environment())

        val listener = DirectBindListener()
        parser.parse(listener)

        println(listener.project)


        val writer = StringWriter()
        context.createMarshaller().marshal(listener.project, writer)
        println(writer)
        return ProjectDirect(project)
    }


    override fun load(reader: Reader) {

    }

    override fun load(inputStream: InputStream) {
    }

    override fun stream(inputStream: InputStream, handler: SuListener) {
    }

}