package eu.k5.soapui.plugin.imex

import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Panel
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.tree.DefaultMutableTreeNode


class ImexView(
    private val model: ImexModel
) {

    private val controller = ImexController(this, model)
    private val mainFrame: JFrame

    private val mainLayout: GridBagLayout

    private val buttonPanel = JPanel()
    private var inputPanel: JPanel

    private val folder: JTextField

    private val diff: DiffView

    private val root: DefaultMutableTreeNode = DefaultMutableTreeNode("Root")

    init {
        mainFrame = JFrame("Synchronize Dialolg")
        mainLayout = GridBagLayout() // BorderLayout(2, 2)
        mainFrame.layout = mainLayout

        
        diff = DiffView(model)

        mainFrame.add(diff)
        mainLayout.addLayoutComponent(diff, BorderLayout.CENTER);


        val inputLayout = GridBagLayout()
        inputPanel = JPanel()
        mainFrame.add(buttonPanel)
        mainLayout.addLayoutComponent(buttonPanel, BorderLayout.SOUTH);
     //   mainFrame.add(inputPanel);
     //   mainLayout.addLayoutComponent(inputPanel, BorderLayout.CENTER);

        folder = addLabelWithInput(inputPanel, inputLayout, "Folder")
        folder.document.addDocumentListener(DocumentChangeListener() { model.folder = folder.text })
        addButtons(buttonPanel, controller)

        mainFrame.pack()
        mainFrame.setSize(500, 300)
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
            inputPanel: JPanel,
            inputLayout: GridBagLayout,
            labelText: String
        ): JTextField {
            val constraints = GridBagConstraints()

            constraints.fill = GridBagConstraints.BOTH
            constraints.weightx = 0.0

            constraints.gridwidth = GridBagConstraints.RELATIVE
            val label = JLabel(labelText)
            inputLayout.setConstraints(label, constraints)
            inputPanel.add(label)

            constraints.weightx = 1.0
            constraints.gridwidth = GridBagConstraints.REMAINDER // next-to-last in
            // row
            val textField = JTextField()
            inputLayout.setConstraints(textField, constraints)
            inputPanel.add(textField)

            return textField
        }


    }
}