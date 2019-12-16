package eu.k5.soapui.plugin.imex

import java.awt.Component
import java.awt.GridBagConstraints
import javax.swing.JPanel

fun constraint(
    gridx: Int,
    gridy: Int,
    fill: Int = GridBagConstraints.HORIZONTAL,
    weightx: Double = 0.0,
    weighty: Double = 0.0,
    gridwidth: Int = 1
): GridBagConstraints {
    val constraint = GridBagConstraints()
    constraint.gridx = gridx
    constraint.gridy = gridy
    constraint.fill = fill
    constraint.weighty = weighty
    constraint.weightx = weightx
    constraint.gridwidth = gridwidth
    return constraint
}
