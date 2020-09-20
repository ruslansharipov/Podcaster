package ru.sharipov.podcaster.i_network.network

import io.reactivex.Flowable

/**
 * Расширение для коллекции Transformable объектов
 */
fun <R, T : Transformable<R>> Collection<T>?.transformCollection(): List<R> {
    return TransformUtil.transformCollection(this)
}

fun <A, R, T : TransformableWithArgs<A, R>> Collection<T>?.transformCollectionWithArgs(args: A) : List<R> {
    return this?.map { it.transformWithArgs(args) } ?: emptyList()
}

fun <R, T : Transformable<R>> Flowable<T>.transform(): Flowable<R> {
    return map { it.transform() }
}

fun <R, T : Transformable<R>> Flowable<List<T>>.transformCollection(): Flowable<List<R>> {
    return map { it.transformCollection() }
}
