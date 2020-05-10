package eu.k5.soapui.swing

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
        val callback: (T?, T?) -> Unit
    )

    fun update(newEntry: T?) {
        val copy = synchronized(this) {
            ArrayList(observers)
        }
        val oldEntry = entry
        entry = newEntry
        for (observer in copy) {
            executeCallbacks(observer, oldEntry)
        }
    }

    private fun executeCallbacks(
        observer: Observer<T>,
        oldValue: T?
    ) {
        if (observer.onEdt) {
            if (SwingUtilities.isEventDispatchThread()) {
                // Execute immediately if already on eventdispatchthread
                observer.callback(oldValue, entry)
            } else {
                SwingUtilities.invokeLater { observer.callback(oldValue, entry) }
            }
        } else {
            observer.callback(oldValue, entry)
        }
    }

    private fun addObserver(observer: Observer<T>) {
        synchronized(this) {
            observers.add(observer)
        }
    }

    fun register(callback: (T?, T?) -> Unit): String {
        val newObserver = Observer<T>(UUID.randomUUID().toString(), false, callback)
        addObserver(newObserver)
        return newObserver.key
    }

    fun registerOnEdt(callImmediately: Boolean = true, callback: (T?, T?) -> Unit): String {
        val newObserver = Observer<T>(UUID.randomUUID().toString(), true, callback)
        addObserver(newObserver)
        if (callImmediately) {
            executeCallbacks(newObserver, entry)
        }
        return newObserver.key
    }

    fun getEntry(): T? {
        return entry
    }

    fun replaceOnEdt(oldObserver: String?, callback: (T?, T?) -> Unit): String {
        removeObserver(oldObserver)
        return registerOnEdt(true, callback)
    }

    fun removeObserver(key: String?) {
        if (key == null) {
            return
        }
        synchronized(this) {
            observers.removeIf { it.key == key }
        }
    }
}