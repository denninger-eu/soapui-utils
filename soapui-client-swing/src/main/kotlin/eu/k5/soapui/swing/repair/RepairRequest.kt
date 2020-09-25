package eu.k5.tolerant.soapui.plugin.eu.k5.soapui.plugin.tolerant

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class RepairRequest {

    var name: String? = null

    var request: String? = null

    var converter: Any? = null
    //Configurations? = null

    var converterKey: String? = null

}