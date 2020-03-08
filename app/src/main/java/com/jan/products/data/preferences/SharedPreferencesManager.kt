package com.jan.products.data.preferences

import android.content.Context

class SharedPreferencesManager {

    companion object {
        private const val PREFS_FILENAME = "test_products"
        private const val KEY_FIREBASE_TOKEN = "keyFirebaseToken"
        private const val KEY_EXPIRATION_DATE = "keyExpirationDate"

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

        @JvmStatic
        fun getExpirationDate(context: Context): String {
            val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            var result = prefs.getString(KEY_EXPIRATION_DATE, "-")
            if (result == null) {
                result = "-"
            }
            return result
        }

        @JvmStatic
        fun setExpirationDate(context: Context, expirationDate: String) {
            val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(KEY_EXPIRATION_DATE, expirationDate)
            editor.apply()
        }
    }
}