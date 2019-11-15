package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.model.assertion.*

class AssertionsBox(
    private val yaml: AssertionsYaml,
    private val store: () -> Unit
) : SuuAssertions {


    override val assertions: List<SuuAssertion>
        get() = yaml.assertions!!.map { it.asBox(store) }

    class AssertionsYaml {
        var assertions: MutableList<AssertionYaml<*>>? = ArrayList()
    }

    abstract class AssertionYaml<T : AssertionBox> {
        var name: String? = null
        var enabled: Boolean? = null

        abstract fun asBox(store: () -> Unit): T
    }

    abstract class AssertionBox(
        private val yaml: AssertionYaml<*>,
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

    class AssertionInvalidStatusYaml : AssertionYaml<AssertionInvalidStatusBox>() {
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


    class AssertionValidStatusYaml : AssertionYaml<AssertionValidStatusBox>() {
        var statusCodes: String? = null

        override fun asBox(store: () -> Unit): AssertionValidStatusBox = AssertionValidStatusBox(this, store)
    }

    class AssertionValidStatusBox(
        private val yaml: AssertionValidStatusYaml,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionValidStatus {
        override var statusCodes: String
            get() = yaml.statusCodes ?: ""
            set(value) {
                if (yaml.statusCodes != value) {
                    yaml.statusCodes = value
                    store()
                }
            }
    }


    class AssertionContainsYaml : AssertionYaml<AssertionContainsBox>() {
        var content: String? = null
        var regexp: Boolean? = null
        var caseSensitive: Boolean? = null
        override fun asBox(store: () -> Unit): AssertionContainsBox = AssertionContainsBox(this, store)
    }

    class AssertionContainsBox(
        private val yaml: AssertionContainsYaml,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionContains {
        override var content: String?
            get() = yaml.content
            set(value) {
                if (yaml.content != value) {
                    yaml.content = value
                    store()
                }
            }
        override var regexp: Boolean
            get() = yaml.regexp ?: false
            set(value) {
                if (yaml.regexp != value) {
                    yaml.regexp = value
                    store()
                }
            }
        override var caseSensitive: Boolean
            get() = yaml.caseSensitive ?: true
            set(value) {
                if (yaml.caseSensitive != value) {
                    yaml.caseSensitive = value
                    store()
                }
            }
    }


    class AssertionNotContainsYaml : AssertionYaml<AssertionNotContainsBox>() {
        var content: String? = null
        var regexp: Boolean? = null
        var caseSensitive: Boolean? = null
        override fun asBox(store: () -> Unit): AssertionNotContainsBox = AssertionNotContainsBox(this, store)
    }

    class AssertionNotContainsBox(
        private val yaml: AssertionNotContainsYaml,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionNotContains {
        override var content: String?
            get() = yaml.content
            set(value) {
                if (yaml.content != value) {
                    yaml.content = value
                    store()
                }
            }
        override var regexp: Boolean
            get() = yaml.regexp ?: false
            set(value) {
                if (yaml.regexp != value) {
                    yaml.regexp = value
                    store()
                }
            }
        override var caseSensitive: Boolean
            get() = yaml.caseSensitive ?: true
            set(value) {
                if (yaml.caseSensitive != value) {
                    yaml.caseSensitive = value
                    store()
                }
            }
    }

    class AssertionScriptYaml : AssertionYaml<AssertionScriptBox>() {
        var script: String? = null

        override fun asBox(store: () -> Unit): AssertionScriptBox = AssertionScriptBox(this, store)
    }

    class AssertionScriptBox(
        private val yaml: AssertionScriptYaml,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionScript {
        override var script: String?
            get() = yaml.script
            set(value) {
                if (yaml.script != value) {
                    yaml.script = value
                    store()
                }
            }
    }


    class AssertionDurationYaml : AssertionYaml<AssertionDurationBox>() {
        var time: String? = null

        override fun asBox(store: () -> Unit): AssertionDurationBox = AssertionDurationBox(this, store)
    }

    class AssertionDurationBox(
        private val yaml: AssertionDurationYaml,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionDuration {
        override var time: String?
            get() = yaml.time
            set(value) {
                if (yaml.time != value) {
                    yaml.time = value
                    store()
                }
            }
    }


    private inline fun <reified T : AssertionBox> create(name: String, newInstance: AssertionYaml<T>): T {
        newInstance.name = name
        yaml.assertions!!.add(newInstance)
        return newInstance.asBox(store)
    }

    override fun createInvalidStatus(name: String): SuuAssertionInvalidStatus {
        return create(name, AssertionInvalidStatusYaml())
    }

    override fun createValidStatus(name: String): SuuAssertionValidStatus {
        return create(name, AssertionValidStatusYaml())
    }

    override fun createContains(name: String): SuuAssertionContains {
        return create(name, AssertionContainsYaml())
    }

    override fun createNotContains(name: String): SuuAssertionNotContains {
        return create(name, AssertionNotContainsYaml())
    }

    override fun createDuration(name: String): SuuAssertionDuration {
        return create(name, AssertionDurationYaml())
    }

    override fun createScript(name: String): SuuAssertionScript {
        return create(name, AssertionScriptYaml())
    }

    companion object {

    }

}