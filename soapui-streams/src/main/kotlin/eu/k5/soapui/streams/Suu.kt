package eu.k5.soapui.streams

import eu.k5.soapui.streams.listener.sync.SyncListener
import eu.k5.soapui.streams.model.SuProject

class Suu {


    companion object {

        fun sync(source: SuProject, target: SuProject) {

        }

        fun syncRestService(source: SuProject, target: SuProject, name: String) {

            var restService = target.getRestService(name)
            if (restService == null) {
                restService = target.createRestService(name)
            }

            val listener = SyncListener(source)

            listener.enterProject(Environment(), target)
            restService.apply(listener.createResourceListener())
            listener.exitProject(target)


        }

    }

}