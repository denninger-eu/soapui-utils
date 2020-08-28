package eu.k5.soapui.transform.restassured.model

import eu.k5.soapui.transform.restassured.ast.Field
import eu.k5.soapui.transform.restassured.ast.Method
import eu.k5.soapui.transform.restassured.ast.Statement

class Scenario(
    val name: String
) {

    val imports = HashSet<String>()

    fun addImport(import: String) = imports.add(import)

    val fields = ArrayList<Field>()

    val methods = ArrayList<Method>()

    val init = ArrayList<Statement>()

}