package com.jan.products.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jan.products.data.api.ProductsApi
import com.jan.products.data.model.ExpirationDate
import com.jan.products.data.request.LoginRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val productsApi: ProductsApi) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _loginError = MutableLiveData<Boolean>()
    val loginError: LiveData<Boolean> = _loginError
    private val _expirationDate = MutableLiveData<String>()
    val expirationDate: LiveData<String> = _expirationDate

    fun login(userName: String, password: String) {
        val loginRequest = LoginRequest()
        loginRequest.userName = userName; loginRequest.password = password
        disposable?.add(
            productsApi.login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ExpirationDate>() {
                    override fun onSuccess(t: ExpirationDate) {
                        _loginError.value = false
                        _expirationDate.value = t.expirationDate
                    }

                    override fun onError(e: Throwable) {
                        _loginError.value = true
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