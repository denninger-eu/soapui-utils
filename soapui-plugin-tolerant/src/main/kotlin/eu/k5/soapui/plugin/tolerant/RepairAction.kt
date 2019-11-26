package eu.k5.tolerant.soapui.plugin.eu.k5.soapui.plugin.tolerant

import com.eviware.soapui.impl.wsdl.WsdlRequest
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.plugins.ToolbarPosition
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.xml.bind.JAXB

@ActionConfiguration(actionGroup = "WsdlRequestActions", //
        toolbarPosition = ToolbarPosition.NONE, //
        toolbarIcon = "/favicon.png", //
        description = "Repair Dialog")//
class RepairAction : AbstractSoapUIAction<WsdlRequest>("Repair Dialog", "Repairs Request") {


    override fun perform(request: WsdlRequest, o: Any?) {
        try {
            val tolerantConverter =
                createTolerantConverter(request.operation.`interface`)


            val configuration =
                createConverterConfiguration(request.operation.`interface`)

            val repairRequest = RepairRequest()
            repairRequest.converter = configuration
            repairRequest.converterKey = "standard"
            repairRequest.name = request.name
            repairRequest.request = request.requestContent

            val file = Files.createTempFile("soap", "xml")

            var writer = StringWriter()
            JAXB.marshal(repairRequest, writer)
            Files.write(file, writer.toString().toByteArray(StandardCharsets.UTF_8))


            val builder = ProcessBuilder().command("java", "-jar", "C:/Users/k5/.soapuios/plugins/tolerant-soapui-plugin-0.1-SNAPSHOT.jar", file.toAbsolutePath().toString())
            builder.redirectErrorStream()

            val process = builder.start()

            process.waitFor()

        } catch (e: Throwable) {
            e.printStackTrace()
        }

        System.out.println("repair");
    }
}
