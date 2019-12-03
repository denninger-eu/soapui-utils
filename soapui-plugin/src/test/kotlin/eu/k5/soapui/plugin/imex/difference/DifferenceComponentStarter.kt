package eu.k5.soapui.plugin.imex.difference

import eu.k5.soapui.plugin.imex.loadProject
import eu.k5.soapui.plugin.imex.treetable.TreeTable
import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.direct.model.ProjectDirect
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.SwingUtilities

fun main(args: Array<String>) {

    val wsdlProject = loadProject("TestSuiteProject-soapui-project.xml")

    val wsdlProject2 = loadProject("TestSuiteProjectChanged-soapui-project.xml")

    val suProject = ProjectDirect(wsdlProject)
    val diff = Suu.diff(suProject, ProjectDirect(wsdlProject2))

    val gui = Runnable {
        try {
            //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (e: Exception) {
            e.printStackTrace()
        }

        DifferenceTreeTableMain() { DifferenceTree.init(diff) }.setVisible(true)
    }
    SwingUtilities.invokeLater(gui)
}

class DifferenceTreeTableMain(
    private val treeSource: () -> DifferenceNode
) : JFrame() {
    init {

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        layout = GridLayout(0, 1)

        val treeTableModel = DifferenceDataModel(treeSource())
        val treeTable = TreeTable(treeTableModel)
        val cPane = contentPane

        cPane.add(JScrollPane(treeTable))
        setSize(1000, 800)
        setLocationRelativeTo(null)
    }

    companion object {
        fun createDataStructure(): DifferenceNode {
            val child = DifferenceNode()
            child.name = "Child"
            child.description = "Description"

            val childChild = DifferenceNode()
            childChild.name = "Child"
            childChild.description = "Description"
            child.children.add(childChild)


            val root = DifferenceNode()
            root.name = "name"
            root.description = "description"

            root.children.add(child)
            root.children.add(childChild)
            return root
        }
    }
}