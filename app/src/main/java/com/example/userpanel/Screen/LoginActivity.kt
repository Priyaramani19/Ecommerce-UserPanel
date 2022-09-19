package com.example.userpanel.Screen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.userpanel.R
import com.example.userpanel.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    var googleApiClient: GoogleApiClient? = null
    lateinit var binding: ActivityLoginBinding
    var RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClick()
        windowColor()

    }

    private fun initClick() {

        binding.loginBtn.setOnClickListener {
            binding.loginTxt.isVisible = false
            binding.progressBar.isVisible = true
            binding.loginBtn.setCardBackgroundColor(Color.rgb(1, 131, 83))
            logIn(binding.mailEdt.text.toString(), binding.passwordEdt.text.toString())
        }

        binding.googleLoginBtn.setOnClickListener {
            loginWithGoogle()
        }

        binding.signUpTxt.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun windowColor() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
    }

    private fun logIn(mail: String, password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnSuccessListener { response ->
            Toast.makeText(this, "LongIn Success", Toast.LENGTH_SHORT).show()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            binding.progressBar.isVisible = false
        }
            .addOnFailureListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id)) //you can also use R.string.default_web_client_id
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient!!)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            RC_SIGN_IN -> {

                var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                var account = result?.signInAccount

                logInwithCredantial(account?.idToken.toString())

            }

        }


    }

    private fun logInwithCredantial(idToken: String) {

        var credantial = GoogleAuthProvider.getCredential(idToken, null)

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credantial).addOnCompleteListener { response ->

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
            .addOnFailureListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            }

    }


}
