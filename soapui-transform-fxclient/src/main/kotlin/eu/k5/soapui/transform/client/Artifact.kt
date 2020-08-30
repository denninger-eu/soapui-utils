package eu.k5.soapui.transform.client

import javafx.beans.property.SimpleStringProperty
import tornadofx.ge
import tornadofx.getProperty
import tornadofx.property

class Artifact(name: String, content: String) {

    constructor() : this("", "")

    val nameProperty = SimpleStringProperty(name)
    val typeProperty = SimpleStringProperty("")
    val contentProperty = SimpleStringProperty(content)
    //  var name by nameProperty

}