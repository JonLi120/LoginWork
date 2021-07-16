package com.messon.noodoe.work.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

interface LazyProvider<R, T> {
    operator fun provideDelegate(thisRef: R, prop: KProperty<*>): Lazy<T>
}

inline fun <R, reified T> argumentDelegate(key: String, crossinline provideArguments: (R) -> Bundle?): LazyProvider<R, T> =
    object : LazyProvider<R, T> {
        override fun provideDelegate(thisRef: R, prop: KProperty<*>): Lazy<T> = lazy {
            val bundle = provideArguments(thisRef)
            bundle?.get(key) as T
        }
    }

inline fun <reified T> Fragment.argumentDelegate(key: String): LazyProvider<Fragment, T> {
    return argumentDelegate(key) { it.arguments }
}