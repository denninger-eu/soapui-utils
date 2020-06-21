package eu.k5.soapui.streams.jaxb

import eu.k5.soapui.streams.Loader
import eu.k5.soapui.streams.jaxb.element.ProjectElement
import eu.k5.soapui.streams.jaxb.model.ProjectJaxb
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import java.io.InputStream
import java.io.Reader
import javax.xml.bind.JAXBContext

class JaxbLoader : Loader {

    fun jaxb(inputStream: InputStream): ProjectJaxb {

        val result: Any? = context.createUnmarshaller().unmarshal(inputStream)
        if (result is ProjectElement) {
            return ProjectJaxb(result)
        }
        throw IllegalArgumentException("Unsupported type: " + result?.javaClass)
    }

    override fun bind(inputStream: InputStream): SuProject {
        TODO("Not yet implemented")
    }

    override fun load(reader: Reader) {
        TODO("Not yet implemented")
    }

    override fun load(inputStream: InputStream) {
        TODO("Not yet implemented")
    }

    override fun stream(inputStream: InputStream, handler: SuListener) {
        TODO("Not yet implemented")
    }

    companion object {

        private val context: JAXBContext = JAXBContext.newInstance(ProjectElement::class.java)

    }

}