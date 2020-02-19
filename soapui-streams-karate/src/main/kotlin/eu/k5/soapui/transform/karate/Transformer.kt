package eu.k5.soapui.transform.karate

import eu.k5.soapui.transform.karate.model.Statement

interface Transformer<T> {

    fun header(step: T): Statement

    fun body(step: T): Statement

}