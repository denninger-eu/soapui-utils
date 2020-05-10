package eu.k5.soapui.swing.widget

import java.awt.Component
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.AbstractCellEditor
import javax.swing.JTable
import javax.swing.JTree
import javax.swing.table.TableCellEditor

class TreeTableCellEditor(
    private val tree: JTree,
    private val table: JTable
) : AbstractCellEditor(), TableCellEditor {

    override fun getTableCellEditorComponent(
        table: JTable,
        value: Any,
        isSelected: Boolean,
        r: Int,
        c: Int
    ): Component {
        return tree
    }

    override fun isCellEditable(e: EventObject?): Boolean {
        if (e is MouseEvent) {
            val colunm1 = 0
            val me = e as MouseEvent?
            val doubleClick = 2
            val newME = MouseEvent(
                tree,
                me!!.id,
                me.getWhen(),
                me.modifiers,
                me.x - table.getCellRect(0, colunm1, true).x,
                me.y,
                doubleClick,
                me.isPopupTrigger
            )
            tree.dispatchEvent(newME)
        }
        return false
    }

    override fun getCellEditorValue(): Any? {
        return null
    }

}