package eu.k5.soapui.swing.karate

import eu.k5.soapui.swing.MainModel
import eu.k5.soapui.swing.MainView
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import java.util.prefs.Preferences
import javax.swing.*
import javax.swing.event.ListSelectionEvent


class KarateExporterView(
    private val model: KarateExporterModel
) {


    private val controller = KarateExporterController(model, this)
    private val mainPanel = JPanel() // = JFrame("Karate Export")
    private val mainLayout: BorderLayout = BorderLayout(2, 2)
    private val buttonPanel = JPanel()
    private val content = JTextArea()
    private val artifactsScrollPane: JScrollPane
    private val contentScrollPane = JScrollPane(content)

    private val artifacts: JList<KarateExporterModel.Artifact>

    init {
        mainPanel.layout = mainLayout

        artifacts = JList(model.artifacts)
        artifacts.selectionMode = ListSelectionModel.SINGLE_INTERVAL_SELECTION
        artifacts.layoutOrientation = JList.VERTICAL
        artifacts.visibleRowCount = -1
        artifacts.addListSelectionListener { updateSelectedItem(it) }
        artifactsScrollPane = JScrollPane(artifacts)
        artifactsScrollPane.preferredSize = Dimension(250, 80)

        content.isEditable = false

        model.current.registerOnEdt { old, new -> updateContent() }

        addButtons(buttonPanel, controller, model)


        mainPanel.add(artifactsScrollPane, BorderLayout.WEST);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH)
        /*  mainFrame.pack()
          mainFrame.setSize(800, 800)*/
    }

    private fun updateContent() {
        content.text = model.current.getEntry()?.content ?: ""

    }

    private fun updateSelectedItem(event: ListSelectionEvent) {
        if (!event.valueIsAdjusting && artifacts.selectedIndex >= 0) {
            model.current.update(artifacts.selectedValue)
        }
    }

    fun asPanel(): JPanel = mainPanel


/*    fun display() {
        mainFrame.isVisible = true
    }*/

    fun getTargetFolder(): File? {
        val chooser = JFileChooser()

        val prefs: Preferences = Preferences.userNodeForPackage(KarateExporterView::class.java)

        chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY;

        val parentFolder = File(prefs.get("folder", ""))
        if (parentFolder.exists()) {
            chooser.currentDirectory = parentFolder
        }
        val result = chooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = chooser.selectedFile
            prefs.put("folder", selectedFile.parentFile.canonicalPath)

            return chooser.selectedFile
        } else {
            return null
        }
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