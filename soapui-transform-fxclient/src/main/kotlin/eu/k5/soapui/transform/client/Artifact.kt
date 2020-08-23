package eu.k5.soapui.transform.client

import javafx.beans.property.SimpleStringProperty
import tornadofx.ge
import tornadofx.getProperty
import tornadofx.property

class Artifact(name: String, content: String) {

    val nameProperty = SimpleStringProperty(name)
  //  var name by nameProperty

}