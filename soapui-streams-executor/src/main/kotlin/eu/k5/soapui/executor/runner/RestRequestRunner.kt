package eu.k5.soapui.executor.runner

import eu.k5.soapui.executor.ClientFactory
import eu.k5.soapui.executor.Runner
import eu.k5.soapui.executor.RunnerContext
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class RestRequestRunner(
    private val step: SuuTestStepRestRequest,
    private val clientFactory: ClientFactory
) : Runner {

    override fun initContext(context: RunnerContext) {
        if (step.request.content != null) {
            context.addProperty(requestProperty(step), step.request.content!!)
        }
    }

    override fun run(context: RunnerContext) {
        context.reportStep(step.name)
        val requestBody = context.getExpandedProperty(requestProperty(step))

        val endpoint = context.getProperty("endpoint")


        val request = Request.Builder()
            .url(endpoint + url(step))
            .method(step.baseMethod.httpMethod.toString(), requestBody?.let { RequestBody.create(null, it) })
            .build();


        val response = clientFactory.client().newCall(request).execute()
        val clientResponse = asClientRespone(response)

        println(clientResponse.body)
        context.addProperty(responseProperty(step), clientResponse.body)

    }


    companion object {
        fun requestProperty(step: SuuTestStepRestRequest): String = step.name + ".Request"

        fun responseProperty(step: SuuTestStepRestRequest): String = step.name + ".Response"

        private fun url(step: SuuTestStepRestRequest): String {
            var url = step.baseService.basePath
            for (resource in step.baseResources) {
                if (!url.endsWith("/") && !resource.path!!.startsWith("/")) {
                    url += "/"
                }
                url += resource.path
            }
            println(url)
            return url
        }

        private fun asClientRespone(response: Response): ClientResponse {
            val body = response.body()

            val string = body?.string() ?: ""
            return ClientResponse(string)
        }
    }
}