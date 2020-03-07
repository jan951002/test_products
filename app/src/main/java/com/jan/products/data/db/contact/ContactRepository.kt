package com.jan.products.data.db.contact

import io.reactivex.Single

interface ContactRepository {

    fun insert(contact: Contact): Single<Long>
}