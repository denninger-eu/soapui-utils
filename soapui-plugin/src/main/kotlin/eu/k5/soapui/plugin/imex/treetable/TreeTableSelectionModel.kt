package eu.k5.soapui.plugin.imex.treetable

import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionListener
import javax.swing.tree.DefaultTreeSelectionModel

class TreeTableSelectionModel : DefaultTreeSelectionModel() {

/*    val listSelectionModel: ListSelectionModel
        get() = super.listSelectionModel*/

    init {
        listSelectionModel.addListSelectionListener(ListSelectionListener { })
    }


    fun getSelectionModel(): ListSelectionModel = listSelectionModel


}