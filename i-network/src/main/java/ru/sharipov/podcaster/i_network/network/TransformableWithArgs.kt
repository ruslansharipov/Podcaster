package ru.sharipov.podcaster.i_network.network

interface TransformableWithArgs<A, T> {
    fun transformWithArgs(args: A): T
}