package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.flatten
import eu.k5.soapui.streams.syncWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class AbstractSyncTest : AbstractDirectTest() {

    inline fun <reified T> sync(createAsync: (T) -> Int) {
        val referenceProject = testProject("TestSuiteProject")
        val box = createTempProjectBox("sync_" + T::class.java.simpleName).syncWith(referenceProject)

        val candidates = box.flatten().filterIsInstance<T>()
        assertFalse(candidates.isEmpty(), "No entry of type " + T::class.java.simpleName + " found in project")
        val selected = candidates.first()
        val count = createAsync(selected)

        val beforeSync = AbstractDirectTest.getDifferences(referenceProject, box)
        if (beforeSync.size() != count) {
            throw IllegalArgumentException("Change did not cause $count differences, caused " + beforeSync.size() + ". " + beforeSync.toString())
        }

        box.syncWith(referenceProject)
        val differences = AbstractDirectTest.getDifferences(referenceProject, box)


        assertTrue(differences.isEmpty(), differences.toString())
    }
}