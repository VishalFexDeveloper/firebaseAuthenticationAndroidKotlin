package com.example.firebaseauth

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.example.firebaseauth.databinding.ActivitySpaleshScreenBinding
import com.google.firebase.auth.FirebaseAuth


class SpaleshScreen : AppCompatActivity() {
    private lateinit var bainding : ActivitySpaleshScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        bainding = ActivitySpaleshScreenBinding.inflate(layoutInflater)
        setContentView(bainding.root)

        Handler().postDelayed({

            if (auth.currentUser != null){
                startActivity(Intent(this,VerificationComplit::class.java))
            }else{
                startActivity(Intent(this, GetSart::class.java))
            }
            finish()

        },3000)


        val todo = "ToDO"

        val spannableString = SpannableString(todo)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#727272")),0,1,0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#43A047")),2,todo.length,0)
        bainding.todoText.text = todo

    }
}