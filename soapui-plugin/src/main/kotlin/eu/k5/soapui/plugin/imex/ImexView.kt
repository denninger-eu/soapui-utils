package eu.k5.soapui.plugin.imex

import java.awt.BorderLayout
import java.awt.Panel
import javax.swing.JFrame
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.Button
import java.awt.Label
import javax.swing.BoxLayout
import java.awt.GridBagLayout
import org.jdesktop.swingx.util.WindowUtils.setConstraints
import java.awt.TextField
import java.awt.GridBagConstraints


class ImexView(
    private val model: ImexModel
) {

    private val controller = ImexController(this, model)
    private val mainFrame: JFrame

    private val mainLayout: BorderLayout

    private val buttonPanel = Panel()
    private var inputPanel: Panel

    private val folder: TextField

    init {
        mainFrame = JFrame("Synchronize Dialolg")
        mainLayout = BorderLayout(2, 2)
        mainFrame.setLayout(mainLayout);

        val inputLayout = GridBagLayout()
        inputPanel = Panel(inputLayout)
        mainFrame.add(buttonPanel)
        mainLayout.addLayoutComponent(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(inputPanel);
        mainLayout.addLayoutComponent(inputPanel, BorderLayout.CENTER);


        folder = addLabelWithInput(inputPanel, inputLayout, "Folder")
        addButtons(buttonPanel, controller)

        mainFrame.pack()
        mainFrame.setSize(300, 300)
    }

    fun display() {
        mainFrame.isVisible = true
    }


    companion object {

        private fun addButtons(buttonPanel: Panel, controller: ImexController) {
            BoxLayout(buttonPanel, BoxLayout.X_AXIS)
            val label = Label("")
            buttonPanel.add(label)

            val buttonExport = Button("Export")
            buttonExport.addActionListener { controller.doExport() }
            buttonPanel.add(buttonExport)

            val buttonImport = Button("Import")
            buttonImport.addActionListener { controller.doImport() }
            buttonPanel.add(buttonImport)


            val cancel = Button("Cancel")
            cancel.addActionListener { controller.cancel() }
            buttonPanel.add(cancel)
        }

        private fun addLabelWithInput(inputPanel: Panel, inputLayout: GridBagLayout, labelText: String): TextField {
            val constraints = GridBagConstraints()

            constraints.fill = GridBagConstraints.BOTH
            constraints.weightx = 0.0

            constraints.gridwidth = GridBagConstraints.RELATIVE
            val label = Label(labelText)
            inputLayout.setConstraints(label, constraints)
            inputPanel.add(label)

            constraints.weightx = 1.0
            constraints.gridwidth = GridBagConstraints.REMAINDER // next-to-last in
            // row
            val textField = TextField()
            inputLayout.setConstraints(textField, constraints)
            inputPanel.add(textField)
            return textField
        }
    }
}