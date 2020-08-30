package eu.k5.soapui.transform

import eu.k5.soapui.streams.model.test.SuuTestCase
import java.lang.IllegalArgumentException
import java.util.*

interface Generator {

    val name: String

    fun transform(testCase: SuuTestCase): TransformationResult

    companion object {

        fun byName(name: String): Generator {
            val loader = ServiceLoader.load(Generator::class.java)
            for (generator in loader) {
                if (name == generator.name) {
                    return generator
                }
            }
            throw IllegalArgumentException("No Generator found for $name")
        }

    }
}