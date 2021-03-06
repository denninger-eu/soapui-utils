package eu.k5.soapui.transform.karate

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.*
import java.io.StringWriter


@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    ManualGen().gen()
}

class ManualGen {

    fun gen() {
        val writer = StringWriter()

        val modelWriter = ModelWriter()
        scenario().write(modelWriter)

        println(writer.toString())
    }

    fun scenario(): Scenario {
        val scenario = Scenario("create and retrieve cat")
        scenario.bodies.add(request())
        return scenario
    }

    private fun request(): RequestBlock {
        val block = RequestBlock("Test")
        block.given.expressions.add(DefaultAssignment.url("http://myhost.com/v1/cats"))
        block.given.expressions.add(DefaultAssignment.request("{'x':'y'}"))
        block.When.expressions.add(DefaultCall.method("POST"))
        block.then.expressions.add(Match.status("201"))
        return block
    }


    /**
    Feature: karate 'hello world' example
    Scenario: create and retrieve a cat

    Given url 'http://myhost.com/v1/cats'
    And request { name: 'Billie' }
    When method post
    Then status 201
    And match response == { id: '#notnull', name: 'Billie' }

    Given path response.id
    When method get
    Then status 200
     **/
}