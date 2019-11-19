package eu.k5.soapui.streams.listener.difference

import java.util.*
import kotlin.collections.ArrayList

class Differences {
    private val differences = ArrayList<Difference>()
    private val path = ArrayDeque<Location>()

    fun addChange(path: String) {
        differences.add(
            DifferenceChange(
                ArrayList(this.path).map { it.toString() }.asReversed(),
                path
            )
        )
    }

    fun addAdditional(name: String) {
        differences.add(
            DifferenceAdditional(
                ArrayList(this.path).map { it.toString() }.asReversed(),
                name
            )
        )
    }

    fun pushProject(name: String) = this.path.push(
        Location(
            Type.PROJECT,
            name
        )
    )

    fun pushRestService(name: String) = this.path.push(
        Location(
            Type.REST_SERVICE,
            name
        )
    )

    fun pushResource(name: String) = this.path.push(
        Location(
            Type.RESOURCE,
            name
        )
    )

    fun pushMethod(name: String) = this.path.push(
        Location(
            Type.METHOD,
            name
        )
    )

    fun pushRequest(name: String) = this.path.push(
        Location(
            Type.REQUEST,
            name
        )
    )

    fun push(type: Type, name: String) = this.path.push(
        Location(
            type,
            name
        )
    )

    fun isEmpty(): Boolean = differences.isEmpty()

    fun pop() = path.pop()

    override fun toString(): String = differences.toString()
    fun <T> addChange(name: String, reference: T, actual: T) {
        if (reference != actual) {
            addChange(name)
        }
    }

    fun addChange(type: Type, name: String) {
        val location = Location(type, name)
        differences.add(
            DifferenceChange(
                ArrayList(this.path).map { it.toString() }.asReversed(),
                location.toString()
            )
        )
    }

    fun addMissing(type: Type, name: String) {
        val location = Location(type, name)
        differences.add(
            DifferenceMissing(
                ArrayList(this.path).map { it.toString() }.asReversed(),
                location.toString()
            )
        )
    }

    fun addMissing(name: String) {
        differences.add(
            DifferenceMissing(
                ArrayList(this.path).map { it.toString() }.asReversed(),
                name
            )
        )

    }

    fun size(): Int {
        return differences.size
    }


    enum class Type {
        PROJECT, REST_SERVICE, RESOURCE, METHOD, REQUEST, PROPERTY,

        TEST_SUITE,

        TEST_CASE,

        TEST_STEP,

        ASSERTION
    }

    class Location(
        val type: Type,
        val name: String
    ) {
        override fun toString(): String {
            return "${type.name}:${name}"
        }
    }

}


class DifferenceChange(
    val basePath: List<String>,
    val element: String
) : Difference {

    override fun toString(): String {
        return "Change $basePath.$element"
    }
}

class DifferenceAdditional(
    private val basePath: List<String>,
    private val element: String
) : Difference {

    override fun toString(): String {
        return "Additional $basePath.$element"
    }
}

class DifferenceMissing(
    val basePath: List<String>,
    val element: String
) : Difference {

    override fun toString(): String {
        return "Missing $basePath.$element"
    }
}

interface Difference {

    enum class Type {
        CHANGE, INSERT, DELETE
    }
}