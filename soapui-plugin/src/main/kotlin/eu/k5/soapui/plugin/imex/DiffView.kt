package eu.k5.soapui.plugin.imex

import eu.k5.soapui.plugin.imex.difference.DifferenceDataModel
import eu.k5.soapui.plugin.imex.difference.DifferenceTree
import eu.k5.soapui.plugin.imex.treetable.TreeTable
import eu.k5.soapui.streams.listener.difference.Differences
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane


class DiffView(
    private val model: ImexModel
) : JPanel() {

    private val controller = DiffController(model, this)

    private var empty = true
    private var scrollPane = JScrollPane(JLabel("No differences available"))

    init {

        layout = GridBagLayout()


        val entry = model.differences.getEntry()
        if (entry != null) {
            updateTree(entry)
        }

//        setPreferredSize(Dimension(450, 110))

        val refreshButton = JButton("Refresh")
        refreshButton.addActionListener { controller.refreshDifferences() }
        add(refreshButton, constraint(0, 0))
        add(
            scrollPane,
            constraint(0, 1, gridwidth = 3, weighty = 1.0, weightx = 1.0, fill = GridBagConstraints.BOTH)
        )
        updateTree(model.differences.getEntry())
        model.differences.registerOnEdt { updateTree(it) }
    }

    private fun updateTree(differences: Differences?) {

        if (differences == null) {
            if (!empty) {
                remove(scrollPane)
                scrollPane = JScrollPane(JLabel("No differences available"))
                add(
                    scrollPane,
                    constraint(0, 1, gridwidth = 3, weighty = 1.0, weightx = 1.0, fill = GridBagConstraints.BOTH)
                )
                println("Empty assigned")
                empty = true
            }
            println("Empty no change")
        } else {
            remove(scrollPane)
            val init = DifferenceTree.init(differences)
            scrollPane = JScrollPane(TreeTable(DifferenceDataModel(init)))
            add(
                scrollPane,
                constraint(0, 1, gridwidth = 3, weighty = 1.0, weightx = 1.0, fill = GridBagConstraints.BOTH)
            )
            println("assigned tree")
            empty = false
            repaint()
            revalidate()
        }
    }
}