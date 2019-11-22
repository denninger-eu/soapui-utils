package eu.k5.soapui.streams.model.test

import eu.k5.soapui.streams.model.SuuProperties

interface SuuTestStep {


    var name: String
    var description: String?
    var enabled: Boolean
  //  val properties: SuuProperties
}