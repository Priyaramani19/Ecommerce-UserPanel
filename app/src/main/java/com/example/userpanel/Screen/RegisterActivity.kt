package com.example.userpanel.Screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.userpanel.R
import com.example.userpanel.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClick()
        windowColor()


    }

    private fun initClick() {
        binding.signUpBtn.setOnClickListener {
            signUp(binding.mailEdt.text.toString(), binding.passwordEdt.text.toString())
        }

        binding.logInTxt.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }

    }

    private fun windowColor() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
    }

    private fun signUp(mail: String, password: String) {

        var firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnSuccessListener { response ->

                Toast.makeText(this, "Successfully, Create Your Account.", Toast.LENGTH_SHORT)
                    .show()

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            }

    }

}