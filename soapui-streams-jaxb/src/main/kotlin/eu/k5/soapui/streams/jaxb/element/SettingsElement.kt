package eu.k5.soapui.streams.jaxb.element

import java.io.StringReader
import javax.xml.bind.JAXBContext
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
class SettingsElement {

    @XmlElement(name = "setting", namespace = NAMESPACE)
    var setting: String? = null


    fun entries(): List<FragmentEntry> {
        println(setting)
        val fragment = fragmentContext.createUnmarshaller().unmarshal(StringReader(setting))
        if (fragment is XmlFragment) {
            return fragment.entries ?: emptyList()
        }
        if (fragment is FragmentEntry){
            return listOf(fragment)
        }
        return emptyList()
    }


    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "xml-fragment")
    @XmlRootElement(name = "xml-fragment")
    class XmlFragment {

        @XmlElement(name = "entry", namespace = NAMESPACE)
        var entries: List<FragmentEntry>? = ArrayList()
    }

    @XmlRootElement(name = "entry", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.NONE)
    class FragmentEntry {

        @XmlAttribute(name = "key")
        var key: String? = null

        @XmlAttribute(name = "value")
        var value: String? = null

    }

    companion object {

        val fragmentContext = JAXBContext.newInstance(XmlFragment::class.java, FragmentEntry::class.java)

    }
}