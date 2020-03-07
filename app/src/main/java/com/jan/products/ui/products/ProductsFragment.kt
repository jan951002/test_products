package com.jan.products.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jan.products.R
import com.jan.products.base.BaseFragment
import com.jan.products.factory.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject

class ProductsFragment : BaseFragment() {

    var viewModelFactory: ViewModelFactory? = null
        @Inject set
    private lateinit var productsViewModel: ProductsViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_products
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
        recyclerProducts.layoutManager = LinearLayoutManager(activity!!)
        recyclerProducts.adapter = ProductsAdapter(context!!, productsViewModel, viewLifecycleOwner)
        observableViewModel()
    }

    override fun onResume() {
        productsViewModel.fetchProducts()
        super.onResume()
    }

    private fun observableViewModel() {
        productsViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            run {
                if (products != null && products.isNotEmpty()) {
                    recyclerProducts.visibility = View.VISIBLE
                } else {
                    recyclerProducts.visibility = View.GONE
                }
            }
        })
    }
}
