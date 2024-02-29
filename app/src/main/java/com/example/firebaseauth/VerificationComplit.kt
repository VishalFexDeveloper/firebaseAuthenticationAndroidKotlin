package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.firebaseauth.databinding.ActivitySignUpBinding
import com.example.firebaseauth.databinding.ActivityVerificationBinding
import com.example.firebaseauth.databinding.ActivityVerificationComplit2Binding
import com.google.firebase.auth.FirebaseAuth

class VerificationComplit : AppCompatActivity() {
     private lateinit var bainding : ActivityVerificationComplit2Binding
     private var auth  = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bainding = ActivityVerificationComplit2Binding.inflate(layoutInflater)
        setContentView(bainding.root)

        val imgUri = auth.currentUser?.photoUrl

        Glide.with(this)
            .load(imgUri)
            .into(bainding.googleImg)

        bainding.userResendName.text = auth.currentUser?.displayName

        bainding.googleEmail.text = auth.currentUser?.email

        bainding.logOutBtn.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                Toast.makeText(this, "Sign out successful", Toast.LENGTH_SHORT).show()
            } else {
                // If the user is not signed in, navigate to the sign-up activity
                startActivity(Intent(this, GetSart::class.java))
                finish()
            }
        }




    }
}