package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.box.rest.RestMethodBox
import eu.k5.soapui.streams.box.rest.RestRequestBox
import eu.k5.soapui.streams.box.rest.RestResourceBox
import eu.k5.soapui.streams.box.rest.RestServiceBox
import eu.k5.soapui.streams.box.test.*
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.introspector.Property
import org.yaml.snakeyaml.nodes.NodeTuple
import org.yaml.snakeyaml.nodes.Tag
import org.yaml.snakeyaml.representer.Representer

class YamlContext {
    companion object {

        private fun yamlClasses(): HashMap<Class<*>, String> {
            val tags = HashMap<Class<*>, String>()
            tags[ProjectBox.ProjectYaml::class.java] = "!project"
            tags[RestServiceBox.RestServiceYaml::class.java] = "!restService"
            tags[RestResourceBox.RestResourceYaml::class.java] = "!restResource"
            tags[RestMethodBox.RestMethodYaml::class.java] = "!restMethod"
            tags[RestRequestBox.RestRequestYaml::class.java] = "!restRequest"

            tags[TestSuiteBox.TestSuiteYaml::class.java] = "!testSuite"
            tags[TestCaseBox.TestCaseYaml::class.java] = "!testCase"
            tags[TestStepPropertyTransfersBox.PropertyTransfersYaml::class.java] = "!propertyTransfers"
            tags[TestStepDelayBox.DelayYaml::class.java] = "!delay"
            tags[TestStepRestRequestBox.RestRequestYaml::class.java] = "!restRequestStep"

            tags[AssertionsBox.AssertionsYaml::class.java] = "!assertions"
            tags[AssertionsBox.AssertionInvalidStatusYaml::class.java] = "!invalidStatus"
            tags[AssertionsBox.AssertionValidStatusYaml::class.java] = "!validStatus"
            tags[AssertionsBox.AssertionContainsYaml::class.java] = "!contains"
            tags[AssertionsBox.AssertionNotContainsYaml::class.java] = "!notContains"
            tags[AssertionsBox.AssertionScriptYaml::class.java] = "!script"
            tags[AssertionsBox.AssertionDurationYaml::class.java] = "!duration"

            tags[AssertionsBox.AssertionJsonPathCountYaml::class.java] = "!jsonPathCount"
            tags[AssertionsBox.AssertionJsonPathRegExYaml::class.java] = "!jsonPathRegEx"
            tags[AssertionsBox.AssertionJsonPathMatchYaml::class.java] = "!jsonPathMatch"
            tags[AssertionsBox.AssertionJsonPathExistsYaml::class.java] = "!jsonPathExists"

            tags[AssertionsBox.AssertionXPathYaml::class.java] = "!xpath"
            tags[AssertionsBox.AssertionXQueryYaml::class.java] = "!xquery"


            return tags
        }


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
                        } else if (propertyValue is List<*> && propertyValue.isEmpty()) {
                            null
                        } else if (property?.name == "enabled" && propertyValue == true) {
                            return null
                        } else {
                            super.representJavaBeanProperty(javaBean, property, propertyValue, customTag)
                        }
                    }
                }
                for (classEntry in yamlClasses()) {
                    representer.addClassTag(classEntry.key, Tag(classEntry.value))
                }

                val yaml = Yaml(representer, Box.options)
                for (classEntry in yamlClasses()) {
                    yaml.addTypeDescription(TypeDescription(classEntry.key))
                }
                return yaml
            }

        val YAML_LOAD: Yaml
            get() {
                val constructor = Constructor()
                for (classEntry in yamlClasses()) {
                    constructor.addTypeDescription(
                        TypeDescription(
                            classEntry.key,
                            Tag(classEntry.value)
                        )
                    )
                }
                val yaml = Yaml(constructor)
                return yaml
            }
    }
}