package eu.k5.soapui.streams

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuListener
import java.io.InputStream
import java.io.Reader
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

interface SuLoader {

    fun getName(): String

    fun load(path: Path): SuProject = Files.newInputStream(path).use { load(it) }

    fun load(inputStream: InputStream): SuProject

    companion object {

        fun load(loaderName: String, inputStream: InputStream): SuProject {
            val loaders = ServiceLoader.load(SuLoader::class.java)
            for (loader in loaders) {
                if (loader.getName() == loaderName) {
                    return loader.load(inputStream)
                }
            }
            throw IllegalArgumentException("No SuLoader found for name $loaderName")
        }
    }
}