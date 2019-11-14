package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.PropertiesBox
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestStep
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.introspector.Property
import org.yaml.snakeyaml.nodes.NodeTuple
import org.yaml.snakeyaml.nodes.Tag
import org.yaml.snakeyaml.representer.Representer
import java.util.regex.Pattern

abstract class TestStepBox(
    private val yaml: TestStepYaml
) : SuuTestStep {

    abstract fun store()

    override var name: String
        get() = yaml.name ?: ""
        set(value) {
            if (yaml.name != value) {
                yaml.name = value
                store()
            }
        }

    override var description: String?
        get() = yaml.description
        set(value) {
            if (yaml.description != value) {
                yaml.description = value
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

    override val properties: SuuProperties
            by lazy { PropertiesBox(yaml.properties!!) { store() } }


    abstract class TestStepYaml {
        var name: String? = null
        var description: String? = null
        var enabled: Boolean? = null
        var properties: MutableList<PropertiesBox.PropertyYaml>? = ArrayList()

    }

    companion object {
        val STEP_PATTERN = Pattern.compile("")

        val YAML_DUMPER: Yaml
            get() {
                val representer = object : Representer() {
                    override fun representJavaBeanProperty(
                        javaBean: Any,
                        property: Property?,
                        propertyValue: Any?,
                        customTag: Tag?
                    ): NodeTuple? {
                        // if value of property is null, ignore it.
                        return if (propertyValue == null) {
                            null
                        } else {
                            super.representJavaBeanProperty(javaBean, property, propertyValue, customTag)
                        }
                    }
                }
                representer.addClassTag(
                    TestStepPropertyTransfersBox.PropertyTransfersYaml::class.java,
                    Tag("!propertyTransfers")
                )
                representer.addClassTag(
                    TestStepDelayBox.DelayYaml::class.java,
                    Tag("!delay")
                )
                representer.addClassTag(
                    TestStepRestRequestBox.RestRequestYaml::class.java,
                    Tag("!restRequest")
                )


                val yaml = Yaml(representer, Box.options)
                yaml.addTypeDescription(TypeDescription(TestStepPropertyTransfersBox.PropertyTransfersYaml::class.java))
                yaml.addTypeDescription(TypeDescription(TestStepDelayBox.DelayYaml::class.java))
                yaml.addTypeDescription(TypeDescription(TestStepRestRequestBox.RestRequestYaml::class.java))

                return yaml
            }

        val YAML_LOAD: Yaml
            get() {
                val constructor = Constructor()
                constructor.addTypeDescription(
                    TypeDescription(
                        TestStepPropertyTransfersBox.PropertyTransfersYaml::class.java,
                        Tag("!propertyTransfers")
                    )
                )
                constructor.addTypeDescription(
                    TypeDescription(
                        TestStepDelayBox.DelayYaml::class.java,
                        Tag("!delay")
                    )
                )
                constructor.addTypeDescription(
                    TypeDescription(
                        TestStepRestRequestBox.RestRequestYaml::class.java,
                        Tag("!restRequest")
                    )
                )
                val yaml = Yaml(constructor)
                return yaml
            }


        fun createBox(parent: Box, name: String, yaml: TestStepYaml): Box {
            val box = parent.createOrderedFile("%04d %s.box.yaml", name)
            yaml.name = name
            box.write(YAML_DUMPER, yaml)
            return box
        }
    }
}