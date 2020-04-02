package ru.sharipov.podcaster.i_network.network

/**
 * Расширение для коллекции Transformable объектов
 */
fun <R, T : Transformable<R>> Collection<T>?.transformCollection(): List<R> {
    return TransformUtil.transformCollection(this)
}
