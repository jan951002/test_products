package com.jan.products.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jan.products.data.db.contact.Contact
import com.jan.products.data.db.contact.ContactDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactViewModel @Inject constructor(private val contactDataSource: ContactDataSource) :
    ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _insertContactError = MutableLiveData<Boolean>()
    val insertContactError: LiveData<Boolean> = _insertContactError
    private val _insertContactLoading = MutableLiveData<Boolean>()
    val insertContactLoading: LiveData<Boolean> = _insertContactLoading

    fun insertContact(name: String, email: String, numberPhone: Long) {
        _insertContactLoading.value = true
        val contact = Contact()
        contact.name = name; contact.email = email; contact.numberPhone = numberPhone
        disposable?.add(
            contactDataSource.insert(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Long>() {
                    override fun onSuccess(t: Long) {
                        _insertContactError.value = false
                        _insertContactLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        _insertContactError.value = true
                        _insertContactLoading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}