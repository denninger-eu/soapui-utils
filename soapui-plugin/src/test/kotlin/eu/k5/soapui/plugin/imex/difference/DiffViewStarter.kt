package eu.k5.soapui.plugin.imex.difference

import eu.k5.soapui.plugin.imex.DiffView
import eu.k5.soapui.plugin.imex.ImexController
import eu.k5.soapui.plugin.imex.ImexModel
import eu.k5.soapui.plugin.imex.loadProject
import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.direct.model.ProjectDirect
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.SwingUtilities

fun main(args: Array<String>) {

    val wsdlProject = loadProject("TestSuiteProject-soapui-project.xml")

    val wsdlProject2 = loadProject("TestSuiteProjectChanged-soapui-project.xml")

    val suProject = ProjectDirect(wsdlProject)
    val diff = Suu.diff(suProject, ProjectDirect(wsdlProject2))

    val model = ImexModel(wsdlProject)
    model.differences.update(diff)
    val gui = Runnable {

        try {
            //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (e: Exception) {
            e.printStackTrace()
        }
        DiffViewMain(model).setVisible(true)
    }

    SwingUtilities.invokeLater(gui)
}

class DiffViewMain(
    private val model: ImexModel
) : JFrame() {
    private val diff: DiffView

    init {
        diff = DiffView(model)
        add(JScrollPane(diff))
        setSize(1000, 800)
    }
}