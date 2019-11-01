package com.eviware.soapui.plugins

import java.io.File
import java.security.Provider

class ProductBodyguard : Provider("JailBreaker", 1.0, "Override signature checks") {

    @Synchronized
    fun isKnown(plugin: File): Boolean = true

}