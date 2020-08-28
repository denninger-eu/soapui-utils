package eu.k5.soapui.transform.restassured.segment

import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.extensions.asEntity
import eu.k5.soapui.transform.restassured.ast.Segment
import eu.k5.soapui.transform.restassured.ast.literal.StringLiteral

class PropertyTransfersMethod(
    private val name: String
) : Segment {

    private val transfers = ArrayList<SuuPropertyTransfer>()

    fun addTransfer(transfer: SuuPropertyTransfer) {
        transfers.add(transfer)
    }

    override fun write(writer: ModelWriter): ModelWriter {
        for (transfer in transfers) {
            writer.writeIndention().write("context.transfer(")
            writeTransfer(writer, transfer.source)
            writer.write(").to(")
            writeTransfer(writer, transfer.target)
            writer.write(");").newLine()
        }
        return writer
    }

    private fun writeTransfer(writer: ModelWriter, transfer: SuuPropertyTransfer.Transfer) {
        writer.write(StringLiteral(transfer.asEntity()))

        if (!transfer.expression.isNullOrEmpty()) {
            writer.write(",").write(StringLiteral(transfer.expression!!)).write(",")
                .write(StringLiteral(transfer.language.name))
        }
    }

    /*   * print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty")
       * print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty")
       * print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty")
   */
}