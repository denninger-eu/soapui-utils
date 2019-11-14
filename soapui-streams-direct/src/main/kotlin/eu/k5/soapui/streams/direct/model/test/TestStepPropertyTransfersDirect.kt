package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers


class TestStepPropertyTransfersDirect(
    val propertyTransfer: PropertyTransfersTestStep
) : AbstractTestStepDirect(propertyTransfer), SuuTestStepPropertyTransfers {
    override fun addTransfer(name: String): SuuPropertyTransfer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override val transfers: List<SuuPropertyTransfer>
        get() = IntRange(0, propertyTransfer.transferCount-1).map { propertyTransfer.getTransferAt(it) }.map {
            PropertyTransferDirect(
                it
            )
        }


}