package com.jan.products.util

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Patterns

class Utils {

    companion object {

        @JvmStatic
        fun checkInternetConnection(context: Activity): Boolean {
            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager?.run {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        ?.run {
                            result = when {
                                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                                else -> {
                                    false
                                }
                            }
                        }
                }
            } else {
                connectivityManager?.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            else -> {
                                false
                            }
                        }
                    }
                }
            }
            return result
        }
    }
}