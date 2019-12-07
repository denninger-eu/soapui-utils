package eu.k5.soapui.streams.listener.difference

import java.util.*
import kotlin.collections.ArrayList

class Differences {
    private val differences = ArrayList<Difference>()
    private val path = ArrayDeque<Location>()

    val root = DiffEntry("root", EntityType.ROOT, Difference.Type.NONE)

    private var current: Deque<DiffEntry> = ArrayDeque()

    init {
        current.push(root)
    }

    fun push(type: EntityType, name: String) {
        val entry = DiffEntry(name, type, Difference.Type.NONE)

        current.peek().childs.add(entry)
        current.push(entry)

        this.path.push(Location(type, name))
    }

    fun addDifference(difference: Difference) {
        differences.add(difference)
        current.peek().differences.add(
            DiffEntry(
                difference.element,
                EntityType.ASSERTION,
                difference.type,
                difference.reference,
                difference.actual
            )
        )
    }

    fun addMissing(name: String) = addDifference(DifferenceMissing(path(), name))

    fun addCxange(path: String) = addDifference(DifferenceChange(path(), path))

    fun addAdditional(name: String) = addDifference(DifferenceAdditional(path(), name))


    fun pushProject(name: String) = push(EntityType.PROJECT, name)

    fun pushRestService(name: String) = push(EntityType.REST_SERVICE, name)

    fun pushResource(name: String) = push(EntityType.RESOURCE, name)

    fun pushMethod(name: String) = push(EntityType.METHOD, name)

    fun pushRequest(name: String) = push(EntityType.REQUEST, name)


    fun isEmpty(): Boolean = differences.isEmpty()

    fun pop() {
        path.pop()
        current.pop()
    }

    override fun toString(): String = differences.toString()

    fun <T> addChange(name: String, reference: T, actual: T) {
        if (reference is String? && actual is String?) {
            if (reference.isNullOrEmpty() && actual.isNullOrEmpty()) {
                return
            }
        }
        if (reference != actual) {
            val difference = DifferenceChange(path(), name)
            difference.reference = reference?.toString()
            difference.actual = actual?.toString()
            addDifference(difference)
        }
    }

    fun addChange(type: EntityType, name: String) {
        val location = Location(type, name)
        addDifference(DifferenceChange(path(), location.toString()))
    }

    fun addMissing(type: EntityType, name: String) {
        val location = Location(type, name)
        addDifference(DifferenceMissing(path(), location.toString()))
    }

    fun addAdditional(type: EntityType, name: String) {
        val location = Location(type, name)
        addDifference(DifferenceMissing(path(), location.toString()))
    }

    fun size(): Int {
        return differences.size
    }

    private fun path() = ArrayList(this.path).map { it.toString() }.asReversed()


    enum class EntityType {
        PROJECT, REST_SERVICE, RESOURCE, METHOD, REQUEST, PROPERTY,

        TEST_SUITE,

        TEST_CASE,

        TEST_STEP,

        ASSERTION,

        ROOT,

        WSDL_SERVICE,

        WSDL_OPERATION,

        WSDL_REQUEST
    }

    class Location(
        val type: EntityType,
        val name: String
    ) {
        override fun toString(): String {
            return "${type.name}:${name}"
        }
    }

}


class DifferenceChange(
    val basePath: List<String>,
    override val element: String
) : Difference {

    override val type = Difference.Type.CHANGE

    override var reference: String? = null
    override var actual: String? = null

    override fun toString(): String {
        return "Change $basePath.$element"
    }
}

class DifferenceAdditional(
    private val basePath: List<String>,
    override val element: String
) : Difference {

    override val type = Difference.Type.INSERT


    override var reference: String? = null
    override var actual: String? = null

    override fun toString(): String {
        return "Additional $basePath.$element"
    }
}

class DifferenceMissing(
    val basePath: List<String>,
    override val element: String
) : Difference {

    override val type = Difference.Type.DELETE

    override var reference: String? = null
    override var actual: String? = null


    override fun toString(): String {
        return "Missing $basePath.$element"
    }
}

interface Difference {

    val element: String

    val type: Type

    var reference: String?
    var actual: String?

    enum class Type {
        NONE, CHANGE, INSERT, DELETE
    }
}


class DiffEntry(
    val name: String,
    val entity: Differences.EntityType,
    val type: Difference.Type,
    val reference: String? = null,
    val actual: String? = null
) {

    val childs: MutableList<DiffEntry> = ArrayList()

    val differences: MutableList<DiffEntry> = ArrayList()

    fun hasDifferences(): Boolean {
        if (differences.isNotEmpty()) {
            return true
        }
        return childs.any { it.hasDifferences() }
    }
}


