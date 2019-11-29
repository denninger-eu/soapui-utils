package eu.k5.soapui.streams.direct.model.assertions

import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion
import eu.k5.soapui.streams.model.assertion.*
import java.util.*
import kotlin.collections.HashMap

open class AbstractAssertion(
    private val testAssertion: WsdlMessageAssertion
) : SuuAssertion {

    override var name: String
        get() = mapName(testAssertion.name)
        set(value) {
            val mappedName = mapName(value)
            if (testAssertion.name != mappedName) {
                testAssertion.name = mappedName
            }
        }
    override var enabled: Boolean
        get() = !testAssertion.isDisabled
        set(value) {
            if (testAssertion.isDisabled == value) {
                testAssertion.isDisabled = !value
            }
        }


    companion object {
        private val PROBLEMATIC_NAMES: Map<String, String>

        init {
            val names = HashMap<String, String>()
            names["JsonPath Existence Match"] = "JsonPath Existence Match."
            names["JsonPath Match"] = "JsonPath Match."
            names["JsonPath Count"] = "JsonPath Count."
            names["JsonPath RegEx Match"] = "JsonPath RegEx Match."
            names["XPath Match"] = "XPath Match."
            names["XQuery Match"] = "XQuery Match."
            names["Invalid HTTP Status Codes"] = "Invalid HTTP Status Codes."
            names["Valid HTTP Status Codes"] = "Valid HTTP Status Codes."
            names["Contains"] = "Contains."
            names["Not Contains"] = "Not Contains."
            names["Response SLA"] = "Response SLA."
            names["Script Assertion"] = "Script Assertion."

            PROBLEMATIC_NAMES = Collections.unmodifiableMap(names)
        }

        fun mapName(name: String): String {
            // Avoiding initial names of assertions;
            // using these names will cause problems when two assertions of the same type are created
            if (PROBLEMATIC_NAMES.containsKey(name)) {
                return PROBLEMATIC_NAMES[name] ?: name
            }
            return name
        }
    }
}