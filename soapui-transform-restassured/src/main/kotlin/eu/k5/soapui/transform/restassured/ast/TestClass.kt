package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

abstract class TestClass(
    val visibility: Visibility
) : Writable {

    val imports = HashSet<String>()

    val fields = ArrayList<Field>()

    val methods = ArrayList<Method>()

    val annotations = Annotations()

    var extendsFrom: String? = null

    fun allImports(): List<String> {
        val importSet = HashSet(imports)
        importSet.addAll(annotations.imports)


        val list = ArrayList(importSet)
        list.sort()
        return list
    }

    fun addMethod(method: Method) {
        methods.add(method)
        imports.addAll(method.imports)
    }

    protected fun writeFields(writer: ModelWriter) {
        for (field in fields) {
            writer.writeIndention().write(Visibility.PRIVATE.keyword).write(" ").write(field.type).write(" ")
                .write(field.name).write(";").newLine()
        }
        writer.newLine()
    }

}