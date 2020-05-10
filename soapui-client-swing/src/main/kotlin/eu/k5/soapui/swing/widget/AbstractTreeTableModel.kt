package eu.k5.soapui.swing.widget

import javax.swing.event.EventListenerList
import javax.swing.event.TreeModelEvent
import javax.swing.event.TreeModelListener
import javax.swing.tree.TreePath

abstract class AbstractTreeTableModel(
    protected var _root: Any
) : TreeTableModel {
    protected var listenerList = EventListenerList()

    private val CHANGED = 0
    private val INSERTED = 1
    private val REMOVED = 2
    private val STRUCTURE_CHANGED = 3

    override fun getRoot(): Any {
        return _root
    }


    override fun isLeaf(node: Any): Boolean {
        return getChildCount(node) == 0
    }

    override fun valueForPathChanged(path: TreePath, newValue: Any) {}

    /**
     * Die Methode wird normalerweise nicht aufgerufen.
     */
    override fun getIndexOfChild(parent: Any, child: Any): Int {
        return 0
    }

    override fun addTreeModelListener(l: TreeModelListener) {
        listenerList.add(TreeModelListener::class.java, l)
    }

    override fun removeTreeModelListener(l: TreeModelListener) {
        listenerList.remove(TreeModelListener::class.java, l)
    }

    private fun fireTreeNode(
        changeType: Int,
        source: Any,
        path: Array<Any>,
        childIndices: IntArray,
        children: Array<Any>
    ) {
        val listeners = listenerList.listenerList
        val e = TreeModelEvent(source, path, childIndices, children)
        var i = listeners.size - 2
        while (i >= 0) {
            if (listeners[i] === TreeModelListener::class.java) {

                when (changeType) {
                    CHANGED -> (listeners[i + 1] as TreeModelListener).treeNodesChanged(e)
                    INSERTED -> (listeners[i + 1] as TreeModelListener).treeNodesInserted(e)
                    REMOVED -> (listeners[i + 1] as TreeModelListener).treeNodesRemoved(e)
                    STRUCTURE_CHANGED -> (listeners[i + 1] as TreeModelListener).treeStructureChanged(e)
                    else -> {
                    }
                }

            }
            i -= 2
        }
    }

    protected fun fireTreeNodesChanged(source: Any, path: Array<Any>, childIndices: IntArray, children: Array<Any>) {
        fireTreeNode(CHANGED, source, path, childIndices, children)
    }

    protected fun fireTreeNodesInserted(source: Any, path: Array<Any>, childIndices: IntArray, children: Array<Any>) {
        fireTreeNode(INSERTED, source, path, childIndices, children)
    }

    protected fun fireTreeNodesRemoved(source: Any, path: Array<Any>, childIndices: IntArray, children: Array<Any>) {
        fireTreeNode(REMOVED, source, path, childIndices, children)
    }

    protected fun fireTreeStructureChanged(
        source: Any,
        path: Array<Any>,
        childIndices: IntArray,
        children: Array<Any>
    ) {
        fireTreeNode(STRUCTURE_CHANGED, source, path, childIndices, children)
    }

}