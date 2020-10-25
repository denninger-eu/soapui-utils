package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuTestCase
import eu.k5.soapui.transform.Generator
import eu.k5.soapui.transform.TransformationResult

class KarateGenerator : Generator {
    override val name: String = "karate"

    override fun transform(testCase: SuuTestCase): TransformationResult {

        val environment = Environment()

        val scenario = MainTransformer(environment).transform(testCase)

        environment.write(scenario)

        return getResult(testCase, environment)
    }


    private fun getResult(testCase: SuuTestCase, environment: Environment): TransformationResult {

        val result =
            TransformationResult(testCase.suite.project.name, testCase.suite.name, testCase.name, environment.mainName)
        result.artifacts.add(
            TransformationResult.Artifact(
                environment.mainName,
                TransformationResult.ArtifactType.KARATE,
                environment.mainDocument ?: ""
            )
        )
        for (artifact in environment.artifacts.values) {
            result.artifacts.add(
                TransformationResult.Artifact(
                    artifact.name,
                    TransformationResult.ArtifactType.TEXT,
                    artifact.content
                )
            )
        }
        return result

    }
}