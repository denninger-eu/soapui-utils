package eu.k5.soapui.transform.karate.model

import sun.security.ec.point.ProjectivePoint

class Scenario(
    val description: String
) {

    val statements: MutableList<Statement> = ArrayList()

    fun write(writer: ModelWriter) {
        writer.writeLine("Scenario: ", description)
        writer.incIndent()
        for(statement in statements){
            statement.write(writer)
        }
        writer.decIndent()
    }
}