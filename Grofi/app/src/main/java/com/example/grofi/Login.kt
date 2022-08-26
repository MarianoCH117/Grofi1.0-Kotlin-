package com.example.grofi

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.grofi.databinding.ActivityCreateAccountBinding
import com.example.grofi.databinding.ActivityLoginBinding
import java.util.regex.Pattern


class Login : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val boton = findViewById<Button>(R.id.btnSingIn)
        boton.setOnClickListener{val intent = Intent(this,createAccount::class.java)
            startActivity(intent)}

        /*mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.etEmail.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->

            val email = mBinding.etEmail.text.toString()
            var errorStr: String? = null
            if (!focused){
                if (email.isEmpty()){
                    errorStr = getString(R.string.helper_required)
                }else if (validarEmail(email)){
                    mBinding.tilEmail.isErrorEnabled = false
                }
                else{
                    errorStr = getString((R.string.card_invalid_email))
                }
            }
            if(focused){
                mBinding.tilEmail.isErrorEnabled = false
            }

            mBinding.tilEmail.error = errorStr
        }*/
    }


    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}