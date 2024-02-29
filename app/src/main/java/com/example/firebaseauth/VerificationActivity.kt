package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.example.firebaseauth.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVerificationBinding
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var OTP: String
    private lateinit var phoneNumber: String
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        OTP = intent.getStringExtra("OTP").toString()
        resendToken = (intent.getParcelableExtra("resendToken") as? PhoneAuthProvider.ForceResendingToken)!!
        phoneNumber = intent.getStringExtra("phoneNumber").toString()

        binding.verticalBtn.setOnClickListener {
            val typeOTP = binding.verticalEdit.text.toString()
            if (typeOTP.isNotEmpty()) {
                if (typeOTP.length == 6) {
                    val credentials = PhoneAuthProvider.getCredential(OTP, typeOTP)
                    signInWithPhoneAuthCredential(credentials)
                } else {
                    Toast.makeText(this, "Please enter a correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter an OTP", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@VerificationActivity, "Authenticate is successful", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,VerificationComplit::class.java))
                    finish()
                } else {
                    Log.d("TAG", "signInWithCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this@VerificationActivity, "Invalid verification code", Toast.LENGTH_LONG).show()
                    } else {
                        // Handle other exceptions or just display a general error message
                        Toast.makeText(this@VerificationActivity, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }


}