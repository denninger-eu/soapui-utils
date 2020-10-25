package eu.k5.soapui.cmd

import com.beust.jcommander.JCommander
import com.beust.jcommander.Parameter
import eu.k5.soapui.streams.jaxb.JaxbLoader
import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.transform.TransformationResult
import eu.k5.soapui.transform.karate.KarateGenerator
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


fun main(args: Array<String>) {
    val generate = GenerateKarate()

    JCommander.newBuilder().addObject(generate).build().parse(*args)

    generate.execute()
}

class GenerateKarate {

    @Parameter(names = ["-input"], required = true)
    var input: String? = null

    @Parameter(names = ["-target"], required = true)
    var output: String? = null

    @Parameter(names = ["suiteFilter"])
    var suiteFilter: String = ".*"

    @Parameter(names = ["caseFilter"])
    var caseFilter = ".*"

    fun execute() {
        val suProject = JaxbLoader().load(Paths.get(input))

        val testCases = suProject.testSuites
            .filter(this::accept)
            .flatMap { it.testCases }
            .filter(this::accept)
            .forEach(this::generate)

    }

    private fun generate(testCase: SuuTestCase) {
        val result = KarateGenerator().transform(testCase)
        println("Writing " + result.testSuite + ":" + result.testCase)
        writeResult(result)
    }

    private fun writeResult(result: TransformationResult) {
        val path = Paths.get(output).resolve(escapePath(result.testSuite)).resolve(escapePath(result.testCase))
        Files.createDirectories(path)
        for (artifact in result.artifacts) {
            val artifactPath = path.resolve(artifact.name)
            println("Writing ${artifactPath.toAbsolutePath()}")
            Files.write(artifactPath, artifact.content.toByteArray(StandardCharsets.UTF_8))
        }

    }

    private fun escapePath(path: String): String {
        return path
    }

    private fun accept(suite: SuuTestSuite): Boolean {
        return suite.name.matches(Regex(suiteFilter))
    }

    private fun accept(testCase: SuuTestCase): Boolean {
        return testCase.name.matches(Regex(caseFilter))
    }
}