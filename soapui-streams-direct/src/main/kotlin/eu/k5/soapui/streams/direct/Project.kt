package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService

class Project : SuProject {
    override var name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var description: String?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override val properties: SuuProperties
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val restServices: List<SuuRestService>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun createRestService(name: String): SuuRestService {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val testSuites: List<SuuTestSuite>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun createTestSuite(name: String): SuuTestSuite {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val wsdlServices: List<SuuWsdlService>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun createWsdlService(name: String): SuuWsdlService {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun toXml(): Any? {
        return ""
    }

}
