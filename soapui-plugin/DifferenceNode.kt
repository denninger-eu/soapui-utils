package eu.k5.soapui.plugin.imex.difference

import eu.k5.soapui.streams.listener.difference.Difference
import eu.k5.soapui.streams.listener.difference.Differences

class DifferenceNode {

    var type: Differences.EntityType? = null

    var name: String? = null

    var description: String? = null

    var reference: String? = null

    var actual: String? = null

    val children: MutableList<DifferenceNode> = ArrayList()

    override fun toString(): String {
        return name ?: ""
    }

}