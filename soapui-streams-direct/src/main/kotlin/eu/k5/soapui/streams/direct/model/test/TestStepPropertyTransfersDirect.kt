package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep
import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import java.util.*


class TestStepPropertyTransfersDirect(
    val propertyTransfer: PropertyTransfersTestStep
) : AbstractTestStepDirect(propertyTransfer), SuuTestStepPropertyTransfers {


    override val transfers: List<SuuPropertyTransfer>
        get() = IntRange(0, propertyTransfer.transferCount - 1).map { propertyTransfer.getTransferAt(it) }.map {
            PropertyTransferDirect(
                it
            )
        }

    override fun addTransfer(name: String): SuuPropertyTransfer =
        PropertyTransferDirect(propertyTransfer.addTransfer(name))


}