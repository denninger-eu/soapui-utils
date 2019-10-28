package eu.k5.soapui.streams.direct

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class DirectLoaderTest {


    @Test
    fun test() {

        val inputStream = Files.newInputStream(Paths.get("src", "test", "resources", "restproject-soapui-project.xml"))
        DirectLoader().bind(inputStream)


    }
}
