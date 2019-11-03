/*
package eu.k5.soapui.streams.direct

import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestResource
import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.rest.support.XmlBeansRestParamsTestPropertyHolder
import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.testsuite.TestProperty
import eu.k5.soapui.streams.direct.model.*
import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuListener
import org.slf4j.LoggerFactory

class DirectParser(
    private val project: WsdlProject,
    private val env: Environment
) {

    fun parse(listener: SuListener) {
        val project1 = ProjectDirect(project)
        listener.enterProject(env, project1)
        parseInterfaces(listener)
    }

    private fun parseInterfaces(listener: SuListener) {
        for (iface in project.interfaceList) {
            if (iface is WsdlInterface) {
                listener.createWsdlInterfaceListener()
            } else if (iface is RestService) {
                parseRestService(iface, listener.createResourceListener())
            }
        }
    }

    private fun parseRestService(restService: RestService, listener: SuuRestServiceListener) {
        val directRestService = RestServiceDirect(restService)
        listener.enter(directRestService)
        for (resource in restService.resourceList) {
            parseResource(resource, listener)
        }
        listener.exit(directRestService)
    }

    private fun parseResource(resource: RestResource, listener: SuuRestServiceListener) {
        val directResource = ResourceDirect(resource)
        directResource.parameters.addAll(parseParameters(resource.params))
        listener.enterResource(directResource)

        for (method in resource.restMethodList) {
            parseMethod(method, listener)
        }

        for (childResource in resource.childResourceList) {
            parseResource(childResource, listener)
        }

        listener.exitResource(directResource)
    }

    private fun parseMethod(method: RestMethod, listener: SuuRestServiceListener) {
        val directMethod = RestMethodDirect(method)
        directMethod.parameters.addAll(parseParameters(method.params))
        listener.enterMethod(directMethod)

        for (request in method.requestList) {
            val directRequest = RestRequestDirect(request)
            val requestHeaders = request.requestHeaders

            listener.handleRequest(directRequest)
        }
        listener.exitMethod(directMethod)
    }

    private fun parseParameters(input: Map<String, TestProperty>): List<RestParameter> {
        val parameters = ArrayList<RestParameter>(input.size)
        for (param in input) {
            val value = param.value
            if (value is XmlBeansRestParamsTestPropertyHolder.XmlBeansRestParamProperty) {
                parameters.add(RestParameter(param.key, value.value, value.style.name))
            } else {
                parameters.add(RestParameter(param.key, value.value, null))
            }
        }
        return parameters
    }


    companion object {
        val LOGGER = LoggerFactory.getLogger(DirectParser::class.java)
    }

}*/
