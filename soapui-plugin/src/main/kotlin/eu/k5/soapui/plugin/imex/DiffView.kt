package eu.k5.soapui.plugin.imex

import eu.k5.soapui.plugin.imex.difference.DifferenceDataModel
import eu.k5.soapui.plugin.imex.difference.DifferenceNode
import eu.k5.soapui.plugin.imex.difference.DifferenceTree
import eu.k5.soapui.plugin.imex.treetable.TreeTable
import eu.k5.soapui.streams.listener.difference.Differences
import java.awt.BorderLayout
import java.awt.Panel
import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.AWTEventMulticaster.getListeners
import org.bouncycastle.asn1.x509.X509Name.T


class DiffView(
    private val model: ImexModel,
    private val controller: ImexController
) : JPanel() {

    private val tree: TreeTable
    private val rootNode: DifferenceNode

    init {
        layout = BorderLayout(2, 2)
        rootNode = DifferenceNode()

        val entry = model.differences.getEntry()
        if (entry != null) {
            updateTree(entry)
        }
        setPreferredSize(Dimension(450, 110))


        tree = TreeTable(DifferenceDataModel(rootNode))
        add(tree)
        model.differences.registerOnEdt { updateTree(it) }
    }

    private fun updateTree(differences: Differences) {
        val init = DifferenceTree.init(differences)
        rootNode.children.clear()
        rootNode.children.add(init)
        println("assigned tree")
    }
}