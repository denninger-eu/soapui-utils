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

    private val controller = ImexController(model)
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

        diff = DiffView(model)

        dataPanel.add(Label("Folder"), labelConstraint(0))
        dataPanel.add(Box.createHorizontalStrut(5), constraint(1, 0, weighty = 0.0, weightx = 0.0))
        folder = JTextField()
        dataPanel.add(folder, constraint(2, 0, weightx = 1.0))
        dataPanel.add(JButton("select"), constraint(3, 0))
        folder.document.addDocumentListener(DocumentChangeListener() {
            println(model.folder.getEntry() + " -> " + folder.text)
            model.folder.update(folder.text)
        })

        //   dataPanel.
        dataPanel.add(Label("Diff"), labelConstraint(1))

        dataPanel.add(
            diff,
            constraint(2, 1, fill = GridBagConstraints.BOTH, weighty = 1.0, weightx = 1.0, gridwidth = 2)
        )



        addButtons(buttonPanel, controller, model)

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
            println("Document change")
            action()
        }

        override fun insertUpdate(e: DocumentEvent?) {
            println("Document insert")

            action()
        }

        override fun removeUpdate(e: DocumentEvent?) {
            println("Document remove")

            action()
        }
    }


    companion object {

        private fun labelConstraint(row: Int): GridBagConstraints {
            val constraint = constraint(gridx = 0, gridy = row, weightx = 0.1)
            constraint.anchor = GridBagConstraints.FIRST_LINE_START
            return constraint
        }

        private fun addButtons(buttonPanel: JPanel, controller: ImexController, model: ImexModel) {
            BoxLayout(buttonPanel, BoxLayout.X_AXIS)
            val label = JLabel("")
            buttonPanel.add(label)

            val buttonCreate = JButton("Create")
            buttonCreate.addActionListener { controller.doCreate() }
            buttonCreate.isEnabled = false
            model.createEnabled.registerOnEdt { buttonCreate.isEnabled = it ?: false }
            buttonPanel.add(buttonCreate)

            val buttonExport = JButton("Export")
            buttonExport.addActionListener { controller.doExport() }
            buttonExport.isEnabled = false
            model.exportEnabled.registerOnEdt { buttonExport.isEnabled = it ?: false }
            buttonPanel.add(buttonExport)

            val buttonImport = JButton("Import")
            buttonImport.addActionListener { controller.doImport() }
            buttonImport.isEnabled = false
            model.importEnabled.registerOnEdt { buttonImport.isEnabled = it ?: false }
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