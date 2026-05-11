package com.example.sonara.core.dispatchers

import kotlinx.coroutines.Dispatchers

class DefaultDispatcherProvider : DispatcherProvider {

    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
}