package eu.k5.soapui.executor.runner

import eu.k5.soapui.executor.ClientFactory
import eu.k5.soapui.executor.Runner
import eu.k5.soapui.executor.RunnerContext
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.test.SuuTestStepRestRequest
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class RestRequestRunner(
    private val step: SuuTestStepRestRequest,
    private val clientFactory: ClientFactory
) : Runner {

    private val parameters: Map<String, SuuRestParameter> by lazy { step.allParameters() }

    override fun initContext(context: RunnerContext) {
        if (step.request.content != null) {
            context.addProperty(requestProperty(step), step.request.content!!)
        }
    }

    override fun run(context: RunnerContext) {
        context.reportStep(step.name)
        val requestBody = context.getExpandedProperty(requestProperty(step))


        val parameters = step.allParameters()

        val builder = Request.Builder()
        builder.url(url(context))
        applyHeaders(builder, context)

        val request = builder
            .method(step.baseMethod.httpMethod.toString(), requestBody?.let { RequestBody.create(null, it) })
            .build();


        val response = clientFactory.client().newCall(request).execute()
        val clientResponse = asClientResponse(response)

        println(clientResponse.body)
        context.addProperty(responseProperty(step), clientResponse.body)

    }

    private fun url(context: RunnerContext): HttpUrl {
        val endpoint = context.getProperty("endpoint")

        var url = endpoint + step.baseService.basePath
        for (resource in step.baseResources) {
            if (!url.endsWith("/") && !resource.path!!.startsWith("/")) {
                url += "/"
            }
            url += resource.path
        }
        for (parameter in parameters.values) {
            if (parameter.style == SuuRestParameter.Style.TEMPLATE) {
                url = url.replace("{" + parameter.name + "}", parameter.value)
            }
        }
        val urlBuilder = HttpUrl.parse(context.applyProperties(url))!!.newBuilder()
        for (parameter in parameters.values) {
            if (parameter.style == SuuRestParameter.Style.QUERY) {
                urlBuilder.addQueryParameter(parameter.name, context.applyProperties(parameter.value))
            }
        }
        val build = urlBuilder.build()
        println(build.url().toString())
        return build
    }

    private fun applyHeaders(builder: Request.Builder, context: RunnerContext) {
        for (parameter in parameters.values) {
            builder.addHeader(parameter.name, context.applyProperties(parameter.value))
        }
        for (header in step.request.headers) {
            for (value in header.value) {
                builder.addHeader(header.key, context.applyProperties(value))
            }
        }
    }

    private fun asClientResponse(response: Response): ClientResponse {
        val body = response.body()

        val string = body?.string() ?: ""
        return ClientResponse(string)
    }

    companion object {
        fun requestProperty(step: SuuTestStepRestRequest): String = step.name + ".Request"

        fun responseProperty(step: SuuTestStepRestRequest): String = step.name + ".Response"
    }
}