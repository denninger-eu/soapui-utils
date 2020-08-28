package eu.k5.soapui.transform.restassured

import java.util.regex.Pattern

class BaseTransformer {


    companion object {
        private val ESCAPE_PATTERN = Pattern.compile("""[^_0-9a-zA-Z]""")
        fun escapeVariableName(fileName: String): String {
            return ESCAPE_PATTERN.matcher(fileName).replaceAll("_")
        }
    }
}