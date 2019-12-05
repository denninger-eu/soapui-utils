package eu.k5.soapui.plugin.imex

import eu.k5.soapui.plugin.imex.difference.DifferenceDataModel
import eu.k5.soapui.plugin.imex.difference.DifferenceNode
import eu.k5.soapui.plugin.imex.treetable.TreeTable
import java.awt.BorderLayout
import java.awt.Panel
import javax.swing.JFrame
import javax.swing.JPanel

class DiffView(
    private val model: ImexModel
) : JPanel() {

    private val tree: TreeTable

    init {
        layout = BorderLayout(2, 2)
        val node = DifferenceNode()
        val differenceDataModel = DifferenceDataModel(node)

        tree = TreeTable(differenceDataModel)
        add(tree)
    }

}