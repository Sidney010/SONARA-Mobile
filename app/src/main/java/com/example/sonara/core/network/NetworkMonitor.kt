package com.example.sonara.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMonitor @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val connectivityManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    fun observe(): Flow<NetworkState> = callbackFlow {

        val callback =
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(
                    network: Network
                ) {

                    trySend(
                        NetworkState.Available
                    )
                }

                override fun onLost(
                    network: Network
                ) {

                    trySend(
                        NetworkState.Unavailable
                    )
                }
            }

        connectivityManager.registerDefaultNetworkCallback(
            callback
        )

        awaitClose {

            connectivityManager.unregisterNetworkCallback(
                callback
            )
        }
    }

    fun isConnected(): Boolean {

        val network =
            connectivityManager.activeNetwork
                ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(
                network
            ) ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }
}