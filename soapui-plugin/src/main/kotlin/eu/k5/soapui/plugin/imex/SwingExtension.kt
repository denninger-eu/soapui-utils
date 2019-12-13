package eu.k5.soapui.plugin.imex

import java.awt.Component
import java.awt.GridBagConstraints
import javax.swing.JPanel

fun constraint(
    gridx: Int,
    gridy: Int,
    fill: Int = GridBagConstraints.HORIZONTAL,
    weighty: Double = 0.0
): GridBagConstraints {
    val constraint = GridBagConstraints()
    constraint.gridx = gridx
    constraint.gridy = gridy
    constraint.fill = fill
    constraint.ipadx = 100
    constraint.weighty = weighty
    return constraint
}
