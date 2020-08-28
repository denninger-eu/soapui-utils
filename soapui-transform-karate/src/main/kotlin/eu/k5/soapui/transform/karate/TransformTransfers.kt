package eu.k5.soapui.transform.karate

import eu.k5.soapui.streams.model.test.SuuPropertyTransfer
import eu.k5.soapui.streams.model.test.SuuTestStepPropertyTransfers
import eu.k5.soapui.transform.extensions.asEntity
import eu.k5.soapui.transform.karate.model.Block
import eu.k5.soapui.transform.karate.model.MethodCallExpression
import eu.k5.soapui.transform.karate.model.Statement
import eu.k5.soapui.transform.karate.model.literals.StringLiteral
import eu.k5.soapui.transform.karate.model.statements.Blank

class TransformTransfers(
    private val environment: Environment
) : Transformer<SuuTestStepPropertyTransfers> {
    override fun header(step: SuuTestStepPropertyTransfers): Statement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun body(step: SuuTestStepPropertyTransfers): Statement {

        val block = Block(step.name)
        for (transfer in step.transfers) {
            block.statements.add(environment.base.methodCall(transferCall(transfer)))
        }
        block.statements.add(Blank())
        return block
    }

    private fun transferCall(transfer: SuuPropertyTransfer): MethodCallExpression {
        var transferFrom = if (transfer.source.expression.isNullOrEmpty()) {
            MethodCallExpression(
                environment.ctx, "transfer", listOf(
                    StringLiteral(
                        asEntity(transfer.source)
                    )
                )
            )
        } else {
            MethodCallExpression(
                environment.ctx, "transfer", listOf(
                    StringLiteral(asEntity(transfer.source)),
                    StringLiteral(transfer.source.expression ?: ""),
                    StringLiteral(transfer.source.language.toString())
                )
            )
        }

        if (transfer.target.expression.isNullOrEmpty()) {
            return transferFrom.chain(
                "to", listOf(
                    StringLiteral(asEntity(transfer.target))
                )
            )
        } else {
            return transferFrom.chain(
                "to", listOf(
                    StringLiteral(asEntity(transfer.target)),
                    StringLiteral(transfer.target.expression ?: ""),
                    StringLiteral(transfer.target.language.toString())
                )
            )
        }
    }

    private fun asEntity(target: SuuPropertyTransfer.Transfer): String = target.asEntity()

}