package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box.Companion.changed
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
                if (changed(yaml.name, value)) {
                    yaml.name = value
                    store()
                }
            }
        override var enabled: Boolean
            get() = yaml.enabled ?: true
            set(value) {
                if (changed(yaml.enabled, value)) {
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
                if (changed(yaml.statusCodes, value)) {
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
                if (changed(yaml.statusCodes, value)) {
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
                if (changed(yaml.content, value)) {
                    yaml.content = value
                    store()
                }
            }
        override var regexp: Boolean
            get() = yaml.regexp ?: false
            set(value) {
                if (changed(yaml.regexp, value, false)) {
                    yaml.regexp = value
                    store()
                }
            }
        override var caseSensitive: Boolean
            get() = yaml.caseSensitive ?: true
            set(value) {
                if (changed(yaml.caseSensitive, value, true)) {
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
                if (changed(yaml.content, value)) {
                    yaml.content = value
                    store()
                }
            }
        override var regexp: Boolean
            get() = yaml.regexp ?: false
            set(value) {
                if (changed(yaml.regexp, value, false)) {
                    yaml.regexp = value
                    store()
                }
            }
        override var caseSensitive: Boolean
            get() = yaml.caseSensitive ?: true
            set(value) {
                if (changed(yaml.caseSensitive, value, true)) {
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
                if (changed(yaml.script, value)) {
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
                if (changed(yaml.time, value)) {
                    yaml.time = value
                    store()
                }
            }
    }

    abstract class AssertionJsonPathYaml<T : AssertionBox> : AssertionYaml<T>() {
        var expression: String? = null
        var expectedValue: String? = null
    }

    open class AssertionJsonPathBox<T : AssertionBox>(
        private val yaml: AssertionJsonPathYaml<T>,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionJsonPath {

        override var expression: String?
            get() = yaml.expression
            set(value) {
                if (changed(yaml.expression, value)) {
                    yaml.expression = value
                    store()
                }
            }
        override var expectedContent: String?
            get() = yaml.expectedValue
            set(value) {
                if (changed(yaml.expectedValue, value)) {
                    yaml.expectedValue = value
                    store()
                }
            }
    }

    class AssertionJsonPathExistsYaml : AssertionJsonPathYaml<AssertionJsonPathExistsBox>() {
        override fun asBox(store: () -> Unit): AssertionJsonPathExistsBox = AssertionJsonPathExistsBox(this, store)
    }

    class AssertionJsonPathExistsBox(
        yaml: AssertionJsonPathExistsYaml, store: () -> Unit
    ) : AssertionJsonPathBox<AssertionJsonPathExistsBox>(yaml, store), SuuAssertionJsonPathExists


    class AssertionJsonPathCountYaml : AssertionJsonPathYaml<AssertionJsonPathCountBox>() {
        override fun asBox(store: () -> Unit): AssertionJsonPathCountBox = AssertionJsonPathCountBox(this, store)
    }

    class AssertionJsonPathCountBox(
        yaml: AssertionJsonPathCountYaml, store: () -> Unit
    ) : AssertionJsonPathBox<AssertionJsonPathCountBox>(yaml, store), SuuAssertionJsonPathCount


    class AssertionJsonPathMatchYaml : AssertionJsonPathYaml<AssertionJsonPathMatchBox>() {
        override fun asBox(store: () -> Unit): AssertionJsonPathMatchBox = AssertionJsonPathMatchBox(this, store)
    }

    class AssertionJsonPathMatchBox(
        yaml: AssertionJsonPathMatchYaml, store: () -> Unit
    ) : AssertionJsonPathBox<AssertionJsonPathMatchBox>(yaml, store), SuuAssertionJsonPathMatch


    class AssertionJsonPathRegExYaml : AssertionJsonPathYaml<AssertionJsonPathRegExBox>() {
        var regularExpression: String? = null
        override fun asBox(store: () -> Unit): AssertionJsonPathRegExBox = AssertionJsonPathRegExBox(this, store)
    }

    class AssertionJsonPathRegExBox(
        private val yaml: AssertionJsonPathRegExYaml,
        private val store: () -> Unit
    ) : AssertionJsonPathBox<AssertionJsonPathRegExBox>(yaml, store), SuuAssertionJsonPathRegEx {
        override var regularExpression: String?
            get() = yaml.regularExpression
            set(value) {
                if (changed(yaml.regularExpression, value)) {
                    yaml.regularExpression = value
                    store();
                }
            }
    }


    abstract class AssertionXmlContains<T : AssertionBox> : AssertionYaml<T>() {
        var expression: String? = null
        var expectedValue: String? = null
        var allowWildcards: Boolean? = null
        var ignoreNamespaceDifferences: Boolean? = null
        var ignoreComments: Boolean? = null
    }

    open class AssertionXmlContainsBox<T : AssertionBox>(
        private val yaml: AssertionXmlContains<T>,
        private val store: () -> Unit
    ) : AssertionBox(yaml, store), SuuAssertionXmlContains {

        override var expression: String?
            get() = yaml.expression
            set(value) {
                if (changed(yaml.expression, value)) {
                    yaml.expression = value
                    store()
                }
            }
        override var expectedContent: String?
            get() = yaml.expectedValue
            set(value) {
                if (changed(yaml.expectedValue, value)) {
                    yaml.expectedValue = value
                    store()
                }
            }

        override var allowWildcards: Boolean
            get() = yaml.allowWildcards ?: false
            set(value) {
                if (changed(yaml.allowWildcards, value, false)) {
                    yaml.allowWildcards = value
                    store()
                }
            }
        override var ignoreNamespaceDifferences: Boolean
            get() = yaml.ignoreNamespaceDifferences ?: false
            set(value) {
                if (changed(yaml.ignoreNamespaceDifferences, value, false)) {
                    yaml.ignoreNamespaceDifferences = value
                    store()
                }
            }
        override var ignoreComments: Boolean
            get() = yaml.ignoreComments ?: false
            set(value) {
                if (changed(yaml.ignoreComments, value, false)) {
                    yaml.ignoreComments = value
                    store()
                }
            }

    }

    class AssertionXPathYaml : AssertionXmlContains<AssertionXPathBox>() {
        override fun asBox(store: () -> Unit): AssertionXPathBox = AssertionXPathBox(this, store)
    }

    class AssertionXPathBox(
        yaml: AssertionXPathYaml, store: () -> Unit
    ) : AssertionXmlContainsBox<AssertionXPathBox>(yaml, store), SuuAssertionXPath

    class AssertionXQueryYaml : AssertionXmlContains<AssertionXQueryBox>() {
        override fun asBox(store: () -> Unit): AssertionXQueryBox = AssertionXQueryBox(this, store)
    }

    class AssertionXQueryBox(
        yaml: AssertionXQueryYaml, store: () -> Unit
    ) : AssertionXmlContainsBox<AssertionXQueryBox>(yaml, store), SuuAssertionXQuery


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

    override fun createJsonPathExists(name: String): SuuAssertionJsonPathExists {
        return create(name, AssertionJsonPathExistsYaml())
    }

    override fun createJsonPathMatch(name: String): SuuAssertionJsonPathMatch {
        return create(name, AssertionJsonPathMatchYaml())
    }

    override fun createJsonPathCount(name: String): SuuAssertionJsonPathCount {
        return create(name, AssertionJsonPathCountYaml())
    }

    override fun createJsonPathRegEx(name: String): SuuAssertionJsonPathRegEx {
        return create(name, AssertionJsonPathRegExYaml())
    }

    override fun createXPath(name: String): SuuAssertionXPath {
        return create(name, AssertionXPathYaml())
    }

    override fun createXQuery(name: String): SuuAssertionXQuery {
        return create(name, AssertionXQueryYaml())
    }


    companion object {

    }

}