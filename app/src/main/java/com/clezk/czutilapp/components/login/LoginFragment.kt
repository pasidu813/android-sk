package com.clezk.czutilapp.components.login

import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.clezk.czutil.loadImage
import com.clezk.czutilapp.R
import com.clezk.czutilapp.util.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment(R.layout.fragment_login) {
    lateinit var disposable: Disposable



    override fun onStart() {
        super.onStart()
        disposable = Observable
            .mergeArray(
                login_btn.clicks().map {
                    Login(login_email.text.toString(), login_password.text.toString())
                }

            )
            .compose(getViewModel(LoginFragmentViewModel::class))
            .subscribe { model ->
                if(model.error){
                    Toast.makeText(context, model.error_message, Toast.LENGTH_SHORT).show()
                }
                if(model.login){
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                }

            }


    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}