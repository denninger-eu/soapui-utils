package eu.k5.soapui.transform.restassured.model

import eu.k5.soapui.transform.restassured.ast.Annotations
import eu.k5.soapui.transform.restassured.ast.literal.ClassLiteral
import eu.k5.soapui.transform.restassured.ast.literal.FieldLiteral
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral

class Environment {


    val baseUrl = "\${#TestCase#baseUrl}"

    var lastStep: String? = null

    fun context() = "SoapuiContext"

    fun contextFqn() = "eu.k5.dread.soapui.SoapuiContext"

    fun propertyHolder() = "PropertyHolder"

    fun propertyHolderFqn() = contextFqn() + "." + propertyHolder()

    fun requestContext() = "RestRequestContext"

    fun requestContextFqn() = contextFqn() + "." + requestContext()


    val restassured = "io.restassured"
    fun restassuredStar() = "static $restassured.RestAssured.*"

    val response = "Response"

    val responseFqn = "$restassured.response.$response"

    val test = Annotations.AnnotationUsage("Test", "org.junit.jupiter.api.Test", emptyList())
    val beforeAll = Annotations.AnnotationUsage("BeforeAll", "org.junit.jupiter.api.BeforeAll", emptyList())

    fun displayName(name: String): Annotations.AnnotationUsage {
        return Annotations.AnnotationUsage(
            "DisplayName",
            "org.junit.jupiter.api.DisplayName",
            listOf(StringLiteral(name))
        )
    }

    fun dependsOn(dependsOn: String): Annotations.AnnotationUsage {
        return Annotations.AnnotationUsage(
            "Dependent.DependsOn",
            "eu.k5.dread.junit.Dependent",
            listOf(StringLiteral(dependsOn))
        )
    }

    fun testMethodOrder(): Annotations.AnnotationUsage {
        return Annotations.AnnotationUsage(
            "TestMethodOrder",
            "org.junit.jupiter.api.TestMethodOrder",
            listOf(ClassLiteral("Dependent.DependsOnMethodOrder"))
        )
    }

    fun extendsWith(): Annotations.AnnotationUsage {
        return Annotations.AnnotationUsage(
            "ExtendWith",
            "org.junit.jupiter.api.extension.ExtendWith",
            listOf(ClassLiteral("Dependent.DependsOnTestWatcher"))
        )
    }

    fun testInstance(): Annotations.AnnotationUsage {
        return Annotations.AnnotationUsage(
            "TestInstance",
            "org.junit.jupiter.api.TestInstance",
            listOf(FieldLiteral("TestInstance.Lifecycle.PER_CLASS"))
        )
    }

}