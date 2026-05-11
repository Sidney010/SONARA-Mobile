package com.example.sonara.core.network

sealed class NetworkState {

    data object Available : NetworkState()

    data object Unavailable : NetworkState()
}