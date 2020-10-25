package eu.k5.soapui.streams.jaxb

import eu.k5.soapui.streams.SuLoader
import eu.k5.soapui.streams.jaxb.element.ProjectElement
import eu.k5.soapui.streams.jaxb.model.ProjectJaxb
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import java.io.InputStream
import java.io.Reader
import javax.xml.bind.JAXBContext

class JaxbLoader : SuLoader {

    override fun getName(): String = "jaxb"



    override fun load(inputStream: InputStream): ProjectJaxb {

        val result: Any? = context.createUnmarshaller().unmarshal(inputStream)
        if (result is ProjectElement) {
            return ProjectJaxb(result)
        }
        throw IllegalArgumentException("Unsupported type: " + result?.javaClass)
    }


    companion object {

        private val context: JAXBContext = JAXBContext.newInstance(ProjectElement::class.java)

    }

}