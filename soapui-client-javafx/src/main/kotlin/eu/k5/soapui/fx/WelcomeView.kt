package eu.k5.soapui.fx

import javafx.scene.Parent
import tornadofx.*

class WelcomeView : View() {
    override val root = borderpane() {
        title = "welcome"
        center {
            vbox {
                label {
                    text = "1. Use \"+\" to open soapui project"
                }
                label {
                    text = "2. Select testcase after soapui project was loaded"
                }
                label {
                    text = "3. Create restassured or karate scripts based on the testcase"
                }

            }
        }
    }

}