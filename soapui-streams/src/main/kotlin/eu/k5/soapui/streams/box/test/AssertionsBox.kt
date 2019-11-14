package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.model.assertion.SuuAssertion
import eu.k5.soapui.streams.model.assertion.SuuAssertionInvalidStatus
import eu.k5.soapui.streams.model.assertion.SuuAssertions

class AssertionsBox(
    private val yaml: AssertionsYaml,
    private val store: () -> Unit
) : SuuAssertions {

    override val assertions: List<SuuAssertion>
        get() = yaml.assertions!!.map { it.asBox(store) }

    class AssertionsYaml {
        var assertions: MutableList<AssertionYaml>? = ArrayList()
    }

    abstract class AssertionYaml {
        var name: String? = null
        var enabled: Boolean? = null

        abstract fun asBox(store: () -> Unit): AssertionBox
    }

    abstract class AssertionBox(
        private val yaml: AssertionYaml,
        private val store: () -> Unit
    ) : SuuAssertion {
        override var name: String
            get() = yaml.name ?: ""
            set(value) {
                if (yaml.name == value) {
                    yaml.name = value
                    store()
                }
            }
        override var enabled: Boolean
            get() = yaml.enabled ?: true
            set(value) {
                if (yaml.enabled != value) {
                    yaml.enabled = value
                    store()
                }
            }

    }

    class AssertionInvalidStatusYaml : AssertionYaml() {
        var statusCodes: String? = null

        override fun asBox(store: () -> Unit): AssertionInvalidStatusBox = AssertionInvalidStatusBox(this, store)
    }

    class AssertionInvalidStatusBox(
        private val yaml: AssertionInvalidStatusYaml,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionInvalidStatus {
        override var statusCodes: String
            get() = yaml.statusCodes ?: ""
            set(value) {
                if (yaml.statusCodes != value) {
                    yaml.statusCodes = value
                    store()
                }
            }
    }


    companion object {

    }

}