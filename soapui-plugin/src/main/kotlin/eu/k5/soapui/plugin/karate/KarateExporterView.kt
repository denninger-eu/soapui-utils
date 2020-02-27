package eu.k5.soapui.plugin.karate

import eu.k5.soapui.plugin.imex.ImexController
import eu.k5.soapui.plugin.imex.ImexModel
import eu.k5.soapui.plugin.imex.ImexView
import java.awt.BorderLayout
import javax.swing.*
import java.awt.Dimension
import javax.swing.event.ListSelectionEvent


class KarateExporterView(
    private val model: KarateExporterModel
) {


    private val controller = KarateExporterController(model)
    private val mainFrame: JFrame = JFrame("Karate Export")
    private val mainLayout: BorderLayout = BorderLayout(2, 2)
    private val buttonPanel = JPanel()
    private val content = JTextArea()
    private val artifactsScrollPane: JScrollPane
    private val contentScrollPane = JScrollPane(content)

    private val artifacts: JList<KarateExporterModel.Artifact>

    init {
        mainFrame.layout = mainLayout

        artifacts = JList(model.artifacts)
        artifacts.selectionMode = ListSelectionModel.SINGLE_INTERVAL_SELECTION
        artifacts.layoutOrientation = JList.VERTICAL
        artifacts.visibleRowCount = -1
        artifacts.addListSelectionListener { updateSelectedItem(it) }
        artifactsScrollPane = JScrollPane(artifacts)
        artifactsScrollPane.preferredSize = Dimension(250, 80)

        content.isEditable = false

        model.current.registerOnEdt { updateContent() }

        addButtons(buttonPanel, controller, model)


        mainFrame.add(artifactsScrollPane, BorderLayout.WEST);
        mainFrame.add(contentScrollPane, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH)
        mainFrame.pack()
        mainFrame.setSize(800, 800)
    }

    private fun updateContent() {
        content.text = model.current.getEntry()?.content ?: ""

    }

    private fun updateSelectedItem(event: ListSelectionEvent) {
        if (!event.valueIsAdjusting && artifacts.selectedIndex >= 0) {
            model.current.update(artifacts.selectedValue)
        }
    }

    fun display() {
        mainFrame.isVisible = true
    }

    companion object {
        private fun addButtons(buttonPanel: JPanel, controller: KarateExporterController, model: KarateExporterModel) {
            BoxLayout(buttonPanel, BoxLayout.X_AXIS)
            val label = JLabel("")
            buttonPanel.add(label)

            val buttonSaveFolder = JButton("Save in Folder")
            buttonSaveFolder.addActionListener { controller.doSaveInFolder() }
            buttonPanel.add(buttonSaveFolder)

            val buttonSaveZip = JButton("Save as zip")
            buttonSaveZip.addActionListener { controller.doSaveZip() }
            buttonPanel.add(buttonSaveZip)
        }
    }
}