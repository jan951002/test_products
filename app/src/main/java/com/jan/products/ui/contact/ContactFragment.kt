package com.jan.products.ui.contact

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jan.products.R
import com.jan.products.base.BaseFragment
import com.jan.products.factory.ViewModelFactory
import javax.inject.Inject

class ContactFragment : BaseFragment() {

    var viewModelFactory: ViewModelFactory? = null
        @Inject set
    private lateinit var contactViewModel: ContactViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_contact
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ContactViewModel::class.java)
    }

}
