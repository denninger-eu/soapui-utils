package eu.k5.soapui.streams.tests

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.difference.DifferenceListener
import eu.k5.soapui.streams.model.SuProject

class SuuAssert {

    fun assertEquals(actual: SuProject, expected: SuProject) {
        val listener = DifferenceListener(expected)
        actual.apply(listener)

        if (listener.differences.isEmpty()) {
            return
        }

        throw AssertionError("Difference [" + listener.differences.size() + "] " + listener.differences)
    }
}