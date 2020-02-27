package eu.k5.soapui.plugin

import java.util.*
import javax.swing.SwingUtilities
import kotlin.collections.ArrayList

class Observable<T>(
    private var entry: T? = null
) {
    private val observers: MutableList<Observer<T>> = ArrayList()

    private class Observer<T>(
        val key: String,
        val onEdt: Boolean,
        val callback: (T) -> Unit
    )

    fun update(newEntry: T) {
        println("new Entry $newEntry")
        val copy = synchronized(this) {
            ArrayList(observers)
        }

        entry = newEntry
        for (observer in copy) {
            if (observer.onEdt) {
                SwingUtilities.invokeLater { observer.callback(newEntry) }
            } else {
                observer.callback(newEntry)
            }
        }
    }

    private fun addObserver(observer: Observer<T>) {
        synchronized(this) {
            observers.add(observer)
        }
    }

    fun register(callback: (T) -> Unit): String {
        val newObserver = Observer(UUID.randomUUID().toString(), false, callback)
        addObserver(newObserver)
        return newObserver.key
    }

    fun registerOnEdt(callback: (T) -> Unit): String {
        val newObserver = Observer(UUID.randomUUID().toString(), true, callback)
        addObserver(newObserver)
        return newObserver.key
    }

    fun getEntry(): T? {
        return entry
    }
}