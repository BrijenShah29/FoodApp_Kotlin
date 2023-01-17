package com.example.mapd711_group8_projectsem1.UserAuthenticationFirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mapd711_group8_projectsem1.MainActivity
import com.example.mapd711_group8_projectsem1.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerBTN.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {

            val email = binding.emailText.text.toString()
            val pass = binding.passText.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {

                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,
                            "Email Id and Password does not Match ! Please try Again !",
                            Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "fill the empty field!!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgetPwdButton.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }




    }
}