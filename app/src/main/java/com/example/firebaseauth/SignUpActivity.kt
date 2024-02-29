package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.userLoginText.setOnClickListener {
            startActivity(Intent(this, LognInActivity::class.java))
            finish()
        }

        binding.userCreateBtn.setOnClickListener {
            val number = binding.userNumber.text.toString()
            if (number.isNotEmpty() && number.length == 10) {
                val formattedNumber = "+91$number"
                sendOtp(formattedNumber)
            } else {
                binding.userNumber.error = "10 digit number enter"
            }
        }
    }

    private fun sendOtp(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    Toast.makeText(this@SignUpActivity, "Verification Failed", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    val intent = Intent(this@SignUpActivity, VerificationActivity::class.java)
                    intent.putExtra("OTP", verificationId)
                    intent.putExtra("resendToken", token)
                    intent.putExtra("phoneNumber", number)
                    signUp(binding.userEmail.text.toString(), binding.userPassword.text.toString())
                    startActivity(intent)
                    super.onCodeSent(verificationId, token)
                    finish()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "email Successfully Singed Up", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "Authenticate isSuccessful", Toast.LENGTH_LONG).show()
                } else {
                    Log.d("TAG", "signInWithCredential: ${task.exception.toString()}")
                    Toast.makeText(this@SignUpActivity, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
