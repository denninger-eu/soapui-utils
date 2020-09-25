package eu.k5.soapui.sync.client

import tornadofx.App

fun main(args: Array<String>) {
    tornadofx.launch<SyncStarter>(*args)
}

class SyncStarter : App(SyncStarterView::class)