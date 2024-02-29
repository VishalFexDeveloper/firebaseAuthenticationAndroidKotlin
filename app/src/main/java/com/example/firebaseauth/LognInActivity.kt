package com.example.firebaseauth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseauth.databinding.ActivityLognInBinding
import com.example.firebaseauth.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LognInActivity : AppCompatActivity() {

    private lateinit var bainding : ActivityLognInBinding
    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bainding = ActivityLognInBinding.inflate(layoutInflater)
        setContentView(bainding.root)

        auth = FirebaseAuth.getInstance()
        
        bainding.tvRedirectLogin.setOnClickListener { 
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        bainding.signUpBtn.setOnClickListener {
            logIn()
        }
    }
    
    
    private fun logIn(){
        val email = bainding.loginEmailEdit.text.toString()
        val password = bainding.loginPasswordEdit.text.toString()

        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }else{

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,VerificationComplit::class.java))
                    finish()
                    return@addOnCompleteListener

                } else {
                    Toast.makeText(this, "not signUp in this email", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }
            }
        }



    }
}