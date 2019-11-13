package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.listener.resource.SyncListener
import eu.k5.soapui.streams.tests.SuuAssert
import org.junit.jupiter.api.Test

class TestSuiteDirectWriteTest : AbstractDirectTest() {


    @Test
    fun writeProjectWithSync() {
        val box = loadFromBox("testsuite")

        val project = DirectLoader().newProject().apply(SyncListener(box)) as ProjectDirect
        SuuAssert().assertEquals(project, box)

        val storedProject = ProjectDirectTest.storeAndReopen("withsync", project)
        SuuAssert().assertEquals(storedProject, box)
    }

}