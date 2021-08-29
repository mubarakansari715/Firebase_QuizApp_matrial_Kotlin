package com.example.firebase_login_reg_matrial.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_login_reg_matrial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = Firebase.auth
        tv_email.text = auth.currentUser?.email
        btn_logout.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure to Logout?")
                .setIcon(R.drawable.icon_logout)
                .setCancelable(false)
            builder.setPositiveButton("LogOut") { BuilderInference, which ->
                Firebase.auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            builder.setNegativeButton("Cancel") { BuilderInference, which ->
                BuilderInference.dismiss()
            }
            builder.show()

        }
    }
}