package com.jan.products.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.jan.products.data.preferences.SharedPreferencesManager

class FirebaseTokenService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.i("TOKEN", refreshedToken)
        refreshedToken?.let {
            SharedPreferencesManager.setFirebaseToken(this, it)
        }
    }
}