package eu.k5.soapui.plugin.imex.difference

import eu.k5.soapui.plugin.imex.treetable.AbstractTreeTableModel
import eu.k5.soapui.plugin.imex.treetable.TreeTableModel
import org.custommonkey.xmlunit.Difference
import java.lang.IllegalArgumentException
import java.util.*

class DifferenceDataModel(
    root: DifferenceNode
) : AbstractTreeTableModel(root) {


    override fun getColumnCount() = columnNames.size

    override fun getColumnName(column: Int) = columnNames[column]

    override fun getColumnClass(column: Int) = columnTypes[column]

    override fun getValueAt(node: Any, column: Int): Any? {
        if (node !is DifferenceNode) {
            return null
        }
        return when (column) {
            0 -> node.name
            1 -> node.reference
            2 -> node.actual
            else -> null
        }
    }

    override fun isCellEditable(node: Any, column: Int) = true

    override fun setValueAt(aValue: Any?, node: Any, column: Int) {
    }

    override fun getChildCount(parent: Any?): Int {
        return if (parent is DifferenceNode) {
            parent.children.size
        } else {
            0
        }
    }

    override fun getChild(parent: Any?, index: Int): Any {
        return if (parent is DifferenceNode && parent.children.size > index) {
            parent.children[index]
        } else {
            throw IllegalArgumentException("Child with index $index does not exist")
        }
    }


    companion object {
        val columnNames = arrayOf("Knotentext", "String", "String")

        protected var columnTypes = arrayOf(
            TreeTableModel::class.java,
            String::class.java,
            String::class.java
        )

    }

}