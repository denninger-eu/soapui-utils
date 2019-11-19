package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.model.TestPropertyHolder
import com.eviware.soapui.model.testsuite.TestProperty
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.SuuProperty

class PropertiesDirect(
    private val propertyHolder: TestPropertyHolder,
    private val filter: List<String> = ArrayList(),
    private val addProperty: (String) -> TestProperty
) : SuuProperties {


    override val properties: List<SuuProperty>
        get() = propertyHolder.propertyList.filter { !filter.contains(it.name) }.map { PropertyDirect(it) }


    override fun remove(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOrUpdate(name: String, value: String?) {
        var property = byName(name) as PropertyDirect
        if (property == null) {
            property = PropertyDirect(addProperty(name))
        }
        property.value = value
    }

    class PropertyDirect(
        private val property: TestProperty
    ) : SuuProperty {

        override val name: String = property.name

        override var value: String?
            get() = property.value
            set(value) {
                property.value = value
            }
    }
}