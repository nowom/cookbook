package nowowiejski.michal.common

import kotlinx.coroutines.CoroutineDispatcher


interface AppDispatcher {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}