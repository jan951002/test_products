package com.jan.products.di.module

import android.app.Application
import androidx.room.Room
import com.jan.products.data.db.TestProductsDatabase
import com.jan.products.data.db.contact.ContactDao
import com.jan.products.data.db.contact.ContactDataSource
import com.jan.products.data.db.contact.ContactRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class RoomModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application?): TestProductsDatabase {
        return Room.databaseBuilder(
                application!!,
                TestProductsDatabase::class.java,
                "test-products-db"
            )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providesContactDao(demoDatabase: TestProductsDatabase): ContactDao {
        return demoDatabase.getContactDao()
    }

    @Singleton
    @Provides
    fun providesContactRepository(contactDao: ContactDao): ContactRepository {
        return ContactDataSource(contactDao)
    }
}