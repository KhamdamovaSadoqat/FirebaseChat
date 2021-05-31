package com.example.firebasechat.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class InternetHelper(val context: Context) {

    val TAG = "InternetHelper"
    lateinit var connectivityManager: ConnectivityManager
    lateinit var snackbar: Snackbar
    var flagOfSnackbar = false

    fun checkInternetConnection(): Boolean {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network: Network = connectivityManager.activeNetwork ?: return false
            val actveNetwork: NetworkCapabilities = connectivityManager.getNetworkCapabilities(
                    network
            ) ?: return false
            Log.d(
                    TAG,
                    "checkInternetConnection: ${actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}"
            )
            return when {
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val networkInfo: NetworkInfo? = (connectivityManager.activeNetworkInfo) as NetworkInfo?
            return networkInfo != null && networkInfo.isConnected
        }
    }

    fun makeSnackbar(view: View, activity: Activity){
        if(!checkInternetConnection()){
            Log.d(TAG, "makeSnackbar: working")
             snackbar =
                Snackbar.make(view, "Internet Connection is lost. Please check your Internet.", Snackbar.LENGTH_INDEFINITE)
//            snackbar.setAction("Turn on") {
//                activity.startActivity(
//                        Intent(
//                                Settings.ACTION_WIFI_SETTINGS
//                        )
//                )
//            }
            snackbar.show()
        }
    }

    fun dissmissSnackbar(){
        Log.d(TAG, "dissmissSnackbar: called")
        if(this::snackbar.isInitialized) {
            Log.d(TAG, "dissmissSnackbar: called")
            this.snackbar.dismiss()
        }
    }
}