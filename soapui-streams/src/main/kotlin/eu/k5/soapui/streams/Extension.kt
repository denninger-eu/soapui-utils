package eu.k5.soapui.streams

import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuListener

fun SuProject.apply(listener: SuListener): SuProject {
    listener.enterProject(Environment(), this)
    for (restService in this.restServices) {
        restService.apply(listener.createResourceListener())
    }
    listener.exitProject(this)
    return this
}

fun SuuRestService.apply(listener: SuuRestServiceListener) {
    listener.enter(this)
    for (resource in this.resources) {
        resource.apply(listener)
    }
    listener.exit(this)
}

fun SuuResource.apply(listener: SuuRestServiceListener) {
    listener.enterResource(this)
    for (method in this.methods) {
        method.apply(listener)
    }
    for (resource in this.resources) {
        resource.apply(listener)
    }
    listener.exitResource(this)
}

fun SuuRestMethod.apply(listener: SuuRestServiceListener) {
    listener.enterMethod(this)
    for (request in this.requests) {
        listener.handleRequest(request)
    }
    listener.exitMethod(this)
}
