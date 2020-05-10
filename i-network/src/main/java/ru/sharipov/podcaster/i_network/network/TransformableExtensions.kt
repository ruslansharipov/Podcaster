package ru.sharipov.podcaster.i_network.network

/**
 * Расширение для коллекции Transformable объектов
 */
fun <R, T : Transformable<R>> Collection<T>?.transformCollection(): List<R> {
    return TransformUtil.transformCollection(this)
}

fun <A, R, T : TransformableWithArgs<A, R>> Collection<T>?.transformCollectionWithArgs(args: A) : List<R> {
    return this?.map { it.transformWithArgs(args) } ?: emptyList()
}
