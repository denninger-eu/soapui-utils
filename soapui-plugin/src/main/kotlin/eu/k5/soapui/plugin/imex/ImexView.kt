package eu.k5.soapui.plugin.imex

import java.awt.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.BoxLayout
import java.awt.Dimension


class ImexView(
    private val model: ImexModel
) {

    private val controller = ImexController(this, model)
    private val mainFrame: JFrame
    private val mainLayout: BorderLayout

    private val dataPanel = JPanel()

    private val buttonPanel = JPanel()

    private val folder: JTextField

    private val diff: DiffView

    private val root: DefaultMutableTreeNode = DefaultMutableTreeNode("Root")

    init {
        mainFrame = JFrame("Synchronize Dialolg")
        mainLayout = BorderLayout(2, 2)
        mainFrame.layout = mainLayout

        dataPanel.layout = GridBagLayout()// BoxLayout(dataPanel, BoxLayout.PAGE_AXIS)

        //folder = addLabelWithInput(dataPanel, "Folder")

        diff = DiffView(model, controller)


        dataPanel.add(Label("Folder"), constraint(0, 0))
        folder = JTextField()
        dataPanel.add(folder, constraint(2, 0))
        folder.document.addDocumentListener(DocumentChangeListener() { model.folder = folder.text })

        //   dataPanel.
        dataPanel.add(Label("Diff"), constraint(0, 1, fill = GridBagConstraints.BOTH, weighty = 1.0))
        val jScrollPane = JScrollPane(diff)

        dataPanel.add(jScrollPane, constraint(2, 1))



        addButtons(buttonPanel, controller)

        mainFrame.add(dataPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.pack()
        mainFrame.setSize(500, 800)
    }

    fun display() {
        mainFrame.isVisible = true
    }

    class DocumentChangeListener(
        private val action: () -> Unit
    ) : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) {
            action()
        }

        override fun insertUpdate(e: DocumentEvent?) {
            action()
        }

        override fun removeUpdate(e: DocumentEvent?) {
            action()
        }
    }


    companion object {


        private fun addButtons(buttonPanel: JPanel, controller: ImexController) {
            BoxLayout(buttonPanel, BoxLayout.X_AXIS)
            val label = JLabel("")
            buttonPanel.add(label)

            val buttonExport = JButton("Export")
            buttonExport.addActionListener { controller.doExport() }
            buttonPanel.add(buttonExport)

            val buttonImport = JButton("Import")
            buttonImport.addActionListener { controller.doImport() }
            buttonPanel.add(buttonImport)

            val cancel = JButton("Cancel")
            cancel.addActionListener { controller.cancel() }
            buttonPanel.add(cancel)
        }

        private fun addLabelWithInput(
            dataPanel: JPanel,
            labelText: String
        ): JTextField {
            val inputPanel = JPanel()
            inputPanel.layout = BoxLayout(inputPanel, BoxLayout.X_AXIS)

            val label = JLabel(labelText)
            inputPanel.add(label)
            inputPanel.add(Box.createRigidArea(Dimension(5, 0)))
            val textField = JTextField()
            inputPanel.add(textField)

            inputPanel.add(Box.createRigidArea(Dimension(5, 0)))
            var button = JButton("select")
            inputPanel.add(button)
            dataPanel.add(inputPanel)

            return textField
        }


    }
}