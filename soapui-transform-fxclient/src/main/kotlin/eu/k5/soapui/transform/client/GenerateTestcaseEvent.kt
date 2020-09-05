package eu.k5.soapui.transform.client

import eu.k5.soapui.streams.model.test.SuuTestCase
import javafx.scene.control.Tab

class GenerateTestcaseEvent(
    val suuTestCase: SuuTestCase,
    val generator: String,
    val tabConsumer: (Tab) -> Any
) : tornadofx.FXEvent()