package eu.k5.soapui.swing

import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode
import javax.swing.tree.TreePath


class ProjectView(
    private val model: MainModel
) {
    private val panel = JPanel()

    private val name: JLabel

    private val parts: JScrollPane

    private val partsTree: JTree

    private var nameObserver: String? = null
    private var structureObserver: String? = null

    private val treeRoot = DefaultMutableTreeNode("root")

    init {
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        name = JLabel("label")
        model.currentProject.register { o, n -> updateCurrent(o, n) }
        panel.add(name)

        partsTree = JTree(treeRoot)
        partsTree.isRootVisible = false
        parts = JScrollPane(partsTree)
        parts.preferredSize = Dimension(250, 80)

        val ml: MouseListener = object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                val selRow: Int = partsTree.getRowForLocation(e.getX(), e.getY())
                val selPath: TreePath = partsTree.getPathForLocation(e.getX(), e.getY())
                if (selRow != -1) {
                    if (e.getClickCount() === 1) {
                        // mySingleClick(selRow, selPath)
                    } else if (e.getClickCount() === 2) {
                        myDoubleClick(selRow, selPath)
                    }
                }
            }
        }
        partsTree.addMouseListener(ml)

        panel.add(parts)
    }

    private fun myDoubleClick(selRow: Int, selPath: TreePath) {
        val component = selPath.lastPathComponent
        if (component !is DefaultMutableTreeNode) {
            return
        }
        val userObject = component.userObject
        if (userObject !is MainModel.TestCaseModel) {
            return
        }
        println("Setting testcase")
        model.currentTestCase.update(userObject.testCase)
    }


    private fun updateCurrent(oldProject: MainModel.ProjectModel?, newProject: MainModel.ProjectModel?) {
        oldProject?.name?.removeObserver(nameObserver)
        oldProject?.structures?.removeObserver(structureObserver)
        nameObserver = newProject?.name?.registerOnEdt { o, n -> name.text = n }
        structureObserver = newProject?.structures?.registerOnEdt { old, new -> updateStructure(old, new) }
    }

    private fun updateStructure(old: List<MutableTreeNode>?, new: List<MutableTreeNode>?) {
        treeRoot.removeAllChildren()
        if (new == null) {
            return
        } else {
            println("Adding structures")
            for (node in new) {
                println(node.toString())
                treeRoot.add(node)

                val treePath = TreePath(arrayOf(treeRoot, node))
                partsTree.makeVisible(treePath)
            }
        }
        partsTree.updateUI()
    }

    fun asPanel(): JPanel = panel

}