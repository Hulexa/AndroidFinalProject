package com.example.snakegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.snakegame.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmpass = binding.confirmPassEt.text.toString()

            if (isValidEmail(email) && isValidPassword(pass) && pass == confirmpass) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                if (!isValidEmail(email)) {
                    Toast.makeText(this, "Invalid Email address", Toast.LENGTH_SHORT).show()
                } else if (!isValidPassword(pass)) {
                    Toast.makeText(
                        this,
                        "Invalid Password. Password must be at least 8 characters long and contain at least one digit, one uppercase letter, and one special character.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        return password.matches(passwordRegex.toRegex())
    }
}
