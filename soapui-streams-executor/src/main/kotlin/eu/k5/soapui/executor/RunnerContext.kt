package eu.k5.soapui.executor

import java.lang.StringBuilder
import java.util.regex.Pattern
import kotlin.collections.HashMap

class RunnerContext {
    private val caseReport = CaseReport()
    private val properties = HashMap<String, String>()

    fun addProperty(key: String, value: String) {
        properties[key] = value
    }

    fun reportStep(name: String) {
        println(name)
        caseReport.reports.add(name)
    }


    fun getProperty(key: String): String? {
        return properties[key]
    }


    fun applyProperties(value: String): String {
        if (value.isNullOrEmpty()) {
            return value
        }

        val resultBuilder = StringBuilder()
        var start = 0
        val matcher = expression.matcher(value)
        while (matcher.find()) {
            if (start != matcher.start()) {
                val before = value.subSequence(start, matcher.start())
                resultBuilder.append(before)
            }
            val property = matcher.group("property")
            resultBuilder.append(properties[property])
            start = matcher.end()
        }
        if (start == 0) {
            return value
        }
        if (start != value.length) {
            resultBuilder.append(value.subSequence(start, value.length))
        }
        return resultBuilder.toString()
    }

    fun getExpandedProperty(key: String): String? {
        val property = getProperty(key)
        if (property != null) {
            return applyProperties(property)
        }
        return null
    }


    companion object {

        val expression: Pattern = Pattern.compile("\\\$\\{(?<property>[-._#a-zA-Z0-9]*)}")
    }

}