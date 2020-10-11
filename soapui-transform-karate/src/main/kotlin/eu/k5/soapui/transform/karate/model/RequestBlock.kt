package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.PrefixBlock

class RequestBlock(
    name: String
) : Statement() {
    val blockComments = ArrayList<Comment>()
    val given = PrefixBlock.Given()
    val When = PrefixBlock.When()
    val then = PrefixBlock.Then()

    init {
        blockComments.add(Comment(name))
    }

    override fun write(writer: ModelWriter): ModelWriter {
        for(comment in blockComments){
            comment.write(writer)
        }
        given.write(writer)
        When.write(writer)
        then.write(writer)
        Blank().write(writer)
        return writer
    }

}