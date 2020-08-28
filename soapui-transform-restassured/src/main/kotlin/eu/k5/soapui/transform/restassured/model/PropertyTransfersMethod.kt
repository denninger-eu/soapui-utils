package eu.k5.soapui.transform.restassured.model

import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.transform.ModelWriter

class PropertyTransfersMethod(
    private val name: String
) : MethodBody() {

    private val transfers = ArrayList<SuuPropertyTransfer>()

    fun addTransfer(transfer: SuuPropertyTransfer) {
        transfers.add(transfer)
    }

    override fun write(writer: ModelWriter) {
        for (transfer in transfers) {
            writer.writeIndention().write("context.transfer(")
            writeTransfer(writer, transfer.source)
            writer.write(").to(")
            writeTransfer(writer, transfer.target)
            writer.write(")").newLine()
        }
    }

    private fun writeTransfer(writer: ModelWriter, transfer: SuuPropertyTransfer.Transfer) {
        writer.write(transfer.propertyName).write(transfer.stepName)

        if (!transfer.expression.isNullOrEmpty()) {
            writer.write(",").write(transfer.expression).write(",").write(transfer.language.name)
        }
    }

    /*   * print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty")
       * print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty")
       * print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty")
   */
}