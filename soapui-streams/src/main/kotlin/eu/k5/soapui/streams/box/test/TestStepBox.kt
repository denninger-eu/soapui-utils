package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.model.test.SuuTestStep
import java.util.regex.Pattern

abstract class TestStepBox(
    private val yaml: TestStepYaml
) : SuuTestStep {

    abstract fun path(): String

    abstract fun store()

    override var name: String
        get() = yaml.name ?: ""
        set(value) {
            if (changed(yaml.name, value)) {
                yaml.name = value
                store()
            }
        }

    override var description: String?
        get() = yaml.description
        set(value) {
            if (changed(yaml.description, value)) {
                yaml.description = value
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

    override var weight: Int
        get() =
            if (yaml.weight != null) {
                yaml.weight!!
            } else {
                extractWeightFromPath()
            }
        set(value) {
            if (value != weight) {
                yaml.weight = value
                store()
            }
        }

    fun extractWeightFromPath(): Int {
        return Integer.parseInt(path().substring(0, 4))
    }

    abstract class TestStepYaml {
        var name: String? = null
        var description: String? = null
        var enabled: Boolean? = null
        var weight: Int? = null
    }

    companion object {
        val STEP_PATTERN = Pattern.compile("")


        fun createBox(parent: Box, name: String, yaml: TestStepYaml): Box {
            val box = parent.createOrderedFile("%04d %s.box.yaml", name)
            yaml.name = name
            box.write(yaml)
            return box
        }
    }
}