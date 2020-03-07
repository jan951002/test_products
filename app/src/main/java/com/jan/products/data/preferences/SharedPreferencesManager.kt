package com.jan.products.data.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    companion object {
        private const val PREFS_FILENAME = "test_products"
        private const val KEY_FIREBASE_TOKEN = "keyFirebaseToken"

        @JvmStatic
        fun getFirebaseToken(context: Context): String {
            val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            var result = prefs.getString(KEY_FIREBASE_TOKEN, "-")
            if (result == null) {
                result = "-"
            }
            return result
        }

        @JvmStatic
        fun setFirebaseToken(context: Context, firebaseToken: String) {
            val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(KEY_FIREBASE_TOKEN, firebaseToken)
            editor.apply()
        }
    }
}