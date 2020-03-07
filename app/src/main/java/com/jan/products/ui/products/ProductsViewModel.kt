package com.jan.products.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jan.products.data.api.ProductsApi
import com.jan.products.data.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val productsApi: ProductsApi) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _products = MutableLiveData<ArrayList<Product>>()
    val products: LiveData<ArrayList<Product>> = _products
    private val _fetchProductsError = MutableLiveData<Boolean>()
    val fetchProductsError: LiveData<Boolean> = _fetchProductsError

    fun fetchProducts() {
        disposable?.add(
            productsApi.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Product>>() {
                    override fun onSuccess(products: ArrayList<Product>) {
                        _fetchProductsError.value = false
                        _products.value = products
                    }

                    override fun onError(e: Throwable) {
                        _fetchProductsError.value = true
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