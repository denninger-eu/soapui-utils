/*
package eu.k5.soapui.plugin.imex

import eu.k5.soapui.streams.Suu
import eu.k5.soapui.streams.direct.model.ProjectDirect
import eu.k5.soapui.streams.model.SuProject
import java.util.concurrent.Executors

class DiffController(
    val model: ImexModel,
    val view: DiffView
) {

    private val executor = Executors.newSingleThreadExecutor()

    init {
        model.target.register { doRefresh() }
    }


    private fun submitCreateDifferences() {
        val targetProject = model.target.getEntry()
        if (targetProject != null) {
            executor.submit() {
                val diff = Suu.diff(ProjectDirect(model.project), targetProject)
                model.differences.update(diff)
            }
        }
    }

    private fun doRefresh() {
        model.differences.update(null)
        submitCreateDifferences()
    }

    fun refreshDifferences() {
        val old = model.folder.getEntry()
        model.folder.update(null)
        model.folder.update(old)
    }


}*/
