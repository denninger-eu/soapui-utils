package eu.k5.soapui.plugin.imex

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
        if (newEntry != entry) {
            val copy = ArrayList(observers)

            entry = newEntry
            for (observer in copy) {
                if (observer.onEdt) {
                    SwingUtilities.invokeLater { observer.callback(newEntry) }
                } else {
                    observer.callback(newEntry)
                }
            }
        }
    }

    fun register(callback: (T) -> Unit): String {
        val newObserver = Observer(UUID.randomUUID().toString(), false, callback)
        observers.add(newObserver)
        return newObserver.key
    }

    fun registerOnEdt(callback: (T) -> Unit): String {
        val newObserver = Observer(UUID.randomUUID().toString(), true, callback)
        observers.add(newObserver)
        return newObserver.key
    }

    fun getEntry(): T? {
        return entry
    }
}