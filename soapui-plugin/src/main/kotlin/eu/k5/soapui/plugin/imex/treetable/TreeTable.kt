package eu.k5.soapui.plugin.imex.treetable

import java.awt.Dimension
import javax.swing.JTable

class TreeTable(
    treeTableModel: AbstractTreeTableModel
) : JTable() {
    private val tree = TreeTableCellRenderer(this, treeTableModel)

    init {

        tree.isRootVisible = false
        // Modell setzen.
        super.setModel(TreeTableModelAdapter(treeTableModel, tree))

        // Gleichzeitiges Selektieren fuer Tree und Table.
        val selectionModel = TreeTableSelectionModel()
        tree.setSelectionModel(selectionModel) //For the tree
        setSelectionModel(selectionModel.getSelectionModel()) //For the table


        // Renderer fuer den Tree.
        setDefaultRenderer(TreeTableModel::class.java, tree)
        // Editor fuer die TreeTable
        setDefaultEditor(TreeTableModel::class.java, TreeTableCellEditor(tree, this))

        // Kein Grid anzeigen.
        setShowGrid(false)

        // Keine Abstaende.
        intercellSpacing = Dimension(0, 0)

    }
}