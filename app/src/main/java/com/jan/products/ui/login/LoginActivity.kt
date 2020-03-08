package com.jan.products.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jan.products.ui.main.MainActivity
import com.jan.products.R
import com.jan.products.base.BaseActivity
import com.jan.products.data.preferences.SharedPreferencesManager
import com.jan.products.factory.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    var viewModelFactory: ViewModelFactory? = null
        @Inject set
    private lateinit var loginViewModel: LoginViewModel

    override fun layoutRes(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        observableViewModel()

        btnLogin.setOnClickListener {
            removeErrors()
            if (validateEmpty()) {
                loginViewModel.login(etUserName.text.toString(), etPassword.text.toString())
            }
        }
    }

    private fun validateEmpty(): Boolean {
        var result = true
        when {
            etUserName.text.toString() == "" -> {
                tilUserName.error = getString(R.string.error_empty_field)
                result = false
            }
            etPassword.text.toString() == "" -> {
                tilPassword.error = getString(R.string.error_empty_field)
                result = false
            }
        }
        return result
    }

    private fun removeErrors() {
        tilUserName.error = null
        tilPassword.error = null
    }

    private fun observableViewModel() {
        loginViewModel.expirationDate.observe(this, Observer { expirationDate ->
            if (expirationDate != null) {
                SharedPreferencesManager.setExpirationDate(this, expirationDate)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("fragmentTag", -1)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        })
    }
}
