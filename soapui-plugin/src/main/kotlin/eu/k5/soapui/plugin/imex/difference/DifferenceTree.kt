package eu.k5.soapui.plugin.imex.difference

import eu.k5.soapui.streams.listener.difference.DiffEntry
import eu.k5.soapui.streams.listener.difference.Differences

class DifferenceTree {

    companion object {
        fun init(differences: Differences): DifferenceNode {
            println(differences.toString())
            return entry(differences.root)
        }

        private fun entry(entry: DiffEntry): DifferenceNode {
            val node = DifferenceNode()
            node.name = entry.name
            for (difference in entry.differences) {
                val differenceNode = DifferenceNode()
                differenceNode.name = difference.name
                differenceNode.reference = difference.reference
                differenceNode.actual = difference.actual
                node.children.add(differenceNode)
            }
            for (childEntry in entry.childs) {
                node.children.add(entry(childEntry))
            }
            return node
        }
    }
}