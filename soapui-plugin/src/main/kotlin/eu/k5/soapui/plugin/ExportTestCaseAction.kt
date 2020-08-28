package eu.k5.soapui.plugin

import com.eviware.soapui.model.testsuite.TestCase
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
/*import eu.k5.soapui.plugin.karate.KarateExporterModel
import eu.k5.soapui.plugin.karate.KarateExporterView*/

@ActionConfiguration(
    actionGroup = "WsdlTestCaseActions", //
    toolbarPosition = ToolbarPosition.NONE, //
    toolbarIcon = "/favicon.png", //
    description = "Quick Repair Tool",
    targetType = TestCase::class
)// https://support.smartbear.com/readyapi/docs/configure/plugins/dev/annotations/actions/action.html
class ExportTestCaseAction : AbstractSoapUIAction<TestCase>("Export Karate", "Synchronize with folder") {

    override fun perform(testCase: TestCase?, p1: Any?) {
/*        println("Do export")
        //   val testSuiteDirect = TestSuiteDirect(testCase as WsdlTestSuite).testCases[0]
        val testSuiteDirect = TestCaseDirect(testCase as WsdlTestCase)

        val transform = KarateTransformer().transform(testSuiteDirect)

        val model = KarateExporterModel()
        model.addArtifact("main.feature", transform.main)

        for (artifact in transform.artifacts) {
            model.addArtifact(artifact.name, artifact.content)
        }

        val view = KarateExporterView(model)
        view.display()*/
    }


}