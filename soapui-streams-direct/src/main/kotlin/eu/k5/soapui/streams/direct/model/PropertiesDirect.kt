package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.model.TestPropertyHolder
import com.eviware.soapui.model.testsuite.TestProperty
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.SuuProperty
import eu.k5.soapui.streams.model.rest.SuuRestParameter

class PropertiesDirect(
    private val propertyHolder: TestPropertyHolder
) : SuuProperties {


    override val properties: List<SuuProperty>
        get() = propertyHolder.propertyList.map { PropertyDirect(it) }


    override fun remove(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOrUpdate(name: String, value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class PropertyDirect(
        private val property: TestProperty
    ) : SuuProperty {

        override val name: String = property.name

        override var value: String
            get() = property.value
            set(value) {
                property.value = value
            }
    }
}