package com.example.firebase_login_reg_matrial.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_login_reg_matrial.MainActivity
import com.example.firebase_login_reg_matrial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialogbox_forget.*
import kotlinx.android.synthetic.main.dialogbox_forget.view.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        btn_Login.setOnClickListener() {
            val email = edt_email_login.text.toString()
            val pass = edt_pass_login.text.toString()
            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(this, "Can't be blank Email and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    } else {
                        Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        tv_forget.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
                .setTitle("Forgot Password")
                .setIcon(R.drawable.ic_baseline_mark_email_unread_24)
                .setMessage("Please Email Address Here")
                .setCancelable(false)
            val view: View = layoutInflater.inflate(R.layout.dialogbox_forget, null)
            val forgot = view.findViewById<EditText>(R.id.edt_forget)
            builder.setView(view)
            builder.setPositiveButton("Reset") { dialogInterface, which ->
                if (forgot.text.isEmpty()) {
                    return@setPositiveButton
                }
                auth.sendPasswordResetEmail(forgot.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("forgetEmail", "Email sent.")
                            Toast.makeText(this, "Sent mail!", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            builder.setNegativeButton("Cancel") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.show()

        }
    }

    fun funSignUpIntent(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}