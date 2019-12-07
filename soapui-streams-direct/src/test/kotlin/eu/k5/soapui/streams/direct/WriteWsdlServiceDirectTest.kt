package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.direct.ReadRestServiceDirectTest.Companion.storeAndReopen
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.listener.sync.SyncListener
import eu.k5.soapui.streams.tests.SuuAssert
import org.junit.jupiter.api.Test

class WriteWsdlServiceDirectTest : AbstractDirectTest() {

    @Test
    fun writeProjectWithSync() {
        val box = loadFromBox("wsdlservice")

        val project = DirectLoader().newProject().apply(SyncListener(box)) as ProjectDirect
        storeAndReopen("withsync_wsdlservice_", project)
        SuuAssert().assertEquals(project, box)

        val storedProject = storeAndReopen("withsync", project)
        SuuAssert().assertEquals(storedProject, box)
    }

}