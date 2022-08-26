package com.example.grofi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class createAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val botonC = findViewById<Button>(R.id.btnCancel)
        botonC.setOnClickListener{onBackPressed()}
    }

}