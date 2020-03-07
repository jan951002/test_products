package com.jan.products.data.db.contact

import io.reactivex.Single
import javax.inject.Inject

class ContactDataSource @Inject constructor(private val contactDao: ContactDao) :
    ContactRepository {

    override fun insert(contact: Contact): Single<Long> {
        return contactDao.insert(contact)
    }
}