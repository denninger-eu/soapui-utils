package eu.k5.tolerant.soapui.plugin.eu.k5.soapui.plugin.tolerant

import eu.k5.tolerant.converter.config.Configurations
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class RepairRequest {

    var name: String? = null

    var request: String? = null

    var converter: Configurations? = null

    var converterKey: String? = null

}