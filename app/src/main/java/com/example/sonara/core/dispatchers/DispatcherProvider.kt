package com.example.sonara.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}