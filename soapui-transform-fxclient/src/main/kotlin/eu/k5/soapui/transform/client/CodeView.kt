/*
package eu.k5.soapui.transform.client

import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class CodeView {


    enum class Style() {
        JSON() {
            private val FINAL_REGEX: Pattern = Pattern.compile(
                JSON_CURLY.toString() + "|" + JSON_PROPERTY + "|" + JSON_VALUE + "|"
                        + JSON_ARRAY + "|" + JSON_BOOL + "|" + JSON_NUMBER
            )
            override fun computeHighlighting(text: String): StyleSpans<Collection<String>> {
                val matcher: Matcher = FINAL_REGEX.matcher(text)
                var lastKwEnd = 0
                val spansBuilder: StyleSpansBuilder<Collection<String>> = StyleSpansBuilder<Collection<String>>()
                while (matcher.find()) {
                    val styleClass =
                        if (matcher.group("JSONPROPERTY") != null) "keyword" else if (matcher.group("JSONVALUE") != null) "value" else if (matcher.group(
                                "JSONARRAY"
                            ) != null
                        ) "array" else if (matcher.group("JSONCURLY") != null) "object" else if (matcher.group("JSONBOOL") != null) "bool" else if (matcher.group(
                                "JSONNUMBER"
                            ) != null
                        ) "number" else "plain"
                    spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd)
                    spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start())
                    lastKwEnd = matcher.end()
                }
                spansBuilder.add(Collections.emptyList(), text.length - lastKwEnd)
                return spansBuilder.create()
            }
        };

        abstract fun computeHighlighting(text: String): StyleSpans<Collection<String>>

    }
}*/
