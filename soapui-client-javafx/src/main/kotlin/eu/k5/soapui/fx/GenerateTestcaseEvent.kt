package eu.k5.soapui.fx

import eu.k5.soapui.streams.model.test.SuuTestCase
import tornadofx.FXEvent

class GenerateTestcaseEvent(
    val suuTestCase: SuuTestCase,
    val generator: String
) : FXEvent()