package eu.k5.soapui.streams.box.test

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.box.BoxImpl
import eu.k5.soapui.streams.box.BoxImpl.Companion.changed
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers

class TestStepPropertyTransfersBox(
    private val box: Box,
    private val yaml: PropertyTransfersYaml = box.load(PropertyTransfersYaml::class.java)

) : TestStepBox(yaml), SuuTestStepPropertyTransfers {


    override val transfers: MutableList<PropertyTransferBox>
            by lazy { yaml.transfers!!.map { PropertyTransferBox(it) { store() } }.toMutableList() }

    override fun addTransfer(name: String): SuuPropertyTransfer {
        val init = transfers
        val propertyTransferYaml = PropertyTransferYaml()
        propertyTransferYaml.name = name
        val box = PropertyTransferBox(propertyTransferYaml) { store() }
        yaml.transfers!!.add(propertyTransferYaml)
        transfers.add(box)
        store()
        return box
    }

    class PropertyTransferBox(
        private val yaml: PropertyTransferYaml,
        private val store: () -> Unit
    ) : SuuPropertyTransfer {

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
        override val source: SuuPropertyTransfer.Transfer
            get() = PropertyReferenceBox(yaml.source!!, store)
        override val target: SuuPropertyTransfer.Transfer
            get() = PropertyReferenceBox(yaml.target!!, store)

    }


    class PropertyReferenceBox(
        private val yaml: PropertyReferenceYaml,
        private val store: () -> Unit
    ) : SuuPropertyTransfer.Transfer {


        override var expression: String?
            get() = yaml.expression
            set(value) {
                if (yaml.expression != value) {
                    yaml.expression = value
                    store()
                }
            }
        override var propertyName: String?
            get() = yaml.propertyName
            set(value) {
                if (yaml.propertyName != value) {
                    yaml.propertyName = value
                    store()
                }
            }
        override var stepName: String?
            get() = yaml.stepName
            set(value) {
                if (yaml.stepName != value) {
                    yaml.stepName = value
                    store()
                }
            }
        override var language: SuuPropertyTransfer.Language
            get() = yaml.language ?: SuuPropertyTransfer.Language.XPATH
            set(value) {
                if (yaml.language != value) {
                    yaml.language = value
                    store()
                }
            }

    }


    class PropertyTransfersYaml : TestStepYaml() {
        var transfers: MutableList<PropertyTransferYaml>? = ArrayList()
    }

    class PropertyTransferYaml {

        var name: String? = null

        var enabled: Boolean? = true

        var source: PropertyReferenceYaml? = PropertyReferenceYaml()

        var target: PropertyReferenceYaml? = PropertyReferenceYaml()
    }


    class PropertyReferenceYaml {
        var stepName: String? = null
        var propertyName: String? = null
        var expression: String? = null
        var language: SuuPropertyTransfer.Language? = null
    }


    override fun store() {
        box.write(yaml)
    }

    companion object {
        fun create(parent: Box, name: String): TestStepPropertyTransfersBox {
            return TestStepPropertyTransfersBox(createBox(parent, name, PropertyTransfersYaml()))
        }
    }
}