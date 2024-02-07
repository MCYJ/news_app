package com.news_app.news_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    lateinit var spf: SharedPreferences
    lateinit var edit: SharedPreferences.Editor
    lateinit var idEditText: EditText
    lateinit var pwEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        spf = getSharedPreferences("login_info", Context.MODE_PRIVATE)
        edit = spf.edit()
        idEditText = findViewById<EditText>(R.id.idEditText)
        pwEditText = findViewById<EditText>(R.id.pwEditText)

        val loginBtn: Button = findViewById(R.id.loginBtn)
        val signUpBtn: Button = findViewById(R.id.signUpBtn)

        loginBtn.setOnClickListener {
            edit.putString("id", idEditText.text.toString())
            edit.putString("pw", pwEditText.text.toString())
            edit.apply()
            startActivity(Intent(this, MainActivity::class.java))
        }

        signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        idEditText.setText(spf.getString("id", ""))
        pwEditText.setText(spf.getString("pw", ""))
    }
}