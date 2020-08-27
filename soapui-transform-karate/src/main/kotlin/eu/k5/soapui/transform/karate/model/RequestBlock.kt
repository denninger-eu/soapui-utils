package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.PrefixBlock

class RequestBlock(
    name: String
) : Statement() {
    val Comment = Comment(name)
    val Given = PrefixBlock.Given()
    val When = PrefixBlock.When()
    val Then = PrefixBlock.Then()

    override fun write(writer: ModelWriter): ModelWriter {
        Comment.write(writer)
        Given.write(writer)
        When.write(writer)
        Then.write(writer)
        Blank().write(writer)
        return writer
    }

}