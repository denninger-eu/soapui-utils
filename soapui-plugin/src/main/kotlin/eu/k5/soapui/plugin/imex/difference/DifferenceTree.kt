package eu.k5.soapui.plugin.imex.difference

import eu.k5.soapui.streams.listener.difference.DiffEntry
import eu.k5.soapui.streams.listener.difference.Differences

class DifferenceTree {

    companion object {
        fun init(differences: Differences): DifferenceNode {
            return entry(differences.root)
        }

        private fun entry(entry: DiffEntry): DifferenceNode {
            val node = DifferenceNode()
            node.name = entry.name
            for (childEntry in entry.childs) {
                node.children.add(entry(childEntry))
            }
            return node
        }
    }
}