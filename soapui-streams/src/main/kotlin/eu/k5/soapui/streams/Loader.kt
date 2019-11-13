package eu.k5.soapui.streams

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuListener
import java.io.InputStream
import java.io.Reader

interface Loader {

    fun bind(inputStream: InputStream): SuProject

    fun load(reader: Reader)

    fun load(inputStream: InputStream)

    fun stream(inputStream: InputStream, handler: SuListener)

}