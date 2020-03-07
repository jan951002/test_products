package com.jan.products.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jan.products.data.db.contact.Contact
import com.jan.products.data.db.contact.ContactDao

@Database(
    entities = [Contact::class],
    version = TestProductsDatabase.VERSION,
    exportSchema = false
)
abstract class TestProductsDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {
        const val VERSION = 1
    }
}