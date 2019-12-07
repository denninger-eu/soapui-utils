package eu.k5.soapui.streams.box

import eu.k5.soapui.streams.box.rest.RestRequestBox
import eu.k5.soapui.streams.model.Header

class HeaderBox {
    class HeaderYaml {
        var key: String? = null
        var values: MutableList<String>? = ArrayList()
    }

    interface WithHeaderYaml {
        var headers: MutableList<HeaderYaml>?
    }

    companion object {
        fun mapHeader(header: HeaderYaml): Header {
            return Header(header.key ?: "", header.values ?: ArrayList())
        }

        fun mapHeader(header: Header): HeaderYaml {
            val yaml = HeaderYaml()
            yaml.key = header.key
            yaml.values = ArrayList(header.value)
            return yaml
        }

        fun handleHeaders(yaml: WithHeaderYaml, header: Header, store: () -> Unit) {
            if (yaml.headers == null) {
                yaml.headers = ArrayList()
                yaml.headers?.add(mapHeader(header))
                store()
            } else {
                val existing = yaml.headers?.firstOrNull { it.key == header.key }
                if (existing != null) {
                    if (existing.values?.equals(header.value) ?: false) {
                        // No change
                        return
                    } else {
                        if (existing.values == null) {
                            existing.values = ArrayList()
                        }
                        existing.values?.clear()
                        existing.values?.addAll(header.value)
                        store()
                    }
                } else {
                    yaml.headers?.add(mapHeader(header))
                    store()
                }
            }
        }
    }
}