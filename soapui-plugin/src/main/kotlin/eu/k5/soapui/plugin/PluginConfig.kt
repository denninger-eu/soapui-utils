package eu.k5.soapui.plugin

import com.eviware.soapui.plugins.PluginAdapter
import com.eviware.soapui.plugins.PluginConfiguration


@PluginConfiguration(
    groupId = "eu.k5.soapui",
    name = "SoapUI Utils",
    version = "1.0.0",
    autoDetect = true,
    description = "Plugin to connect soapui projects with scv",
    infoUrl = "https://bitbucket.org/k5_/soapui-utils"
)
class PluginConfig : PluginAdapter()