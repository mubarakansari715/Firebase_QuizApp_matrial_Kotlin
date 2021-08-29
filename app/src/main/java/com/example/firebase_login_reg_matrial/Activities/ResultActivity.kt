package com.example.firebase_login_reg_matrial.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_login_reg_matrial.MainActivity
import com.example.firebase_login_reg_matrial.Models.Quiz
import com.example.firebase_login_reg_matrial.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    lateinit var quiz: Quiz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        setUpResult()
    }

    private fun setUpResult() {
        val resultData = intent.getStringExtra("Result")
        quiz = Gson().fromJson<Quiz>(resultData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {

    }

    private fun calculateScore() {
        var score = 0
        for (entry in quiz.questions.entries) {
            val question = entry.value
            if (question.answer == question.useranswer) {
                score += 10
            }
        }

        tv_result.text = "Your score is $score"

    }

    fun funHome(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}