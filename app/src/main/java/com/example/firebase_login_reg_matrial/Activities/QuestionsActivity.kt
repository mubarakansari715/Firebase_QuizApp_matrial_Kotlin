package com.example.firebase_login_reg_matrial.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase_login_reg_matrial.Models.QuestionDataClass
import com.example.firebase_login_reg_matrial.Models.Quiz
import com.example.firebase_login_reg_matrial.QuestionAdapter
import com.example.firebase_login_reg_matrial.R
import com.example.firebase_login_reg_matrial.databinding.ActivityQuestionsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {
    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, QuestionDataClass>? = null
    var index = 1

    lateinit var binding: ActivityQuestionsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions)

        FirebaseBindData()
        setEventsBinding()

    }

    private fun setEventsBinding() {
        btn_previous.setOnClickListener {
            index--
            bindView()
        }
        btn_next.setOnClickListener {
            index++
            bindView()
        }
        btn_submit.setOnClickListener {
            Log.d("submit", "${questions.toString()}")
            val i = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            i.putExtra("Result", json)
            startActivity(i)

        }
    }

    private fun FirebaseBindData() {
        val firestore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("Date")
        if (date !== null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    Log.d("dataRetrieved", "open: ${it.toObjects(Quiz::class.java).toString()}")
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindView()


                    }
                }
        }
    }

    fun bindView() {
        btn_previous.visibility = View.GONE
        btn_next.visibility = View.GONE
        btn_submit.visibility = View.GONE

        if (index == 1) {
            btn_next.visibility = View.VISIBLE
        } else if (index == questions!!.size) {
            btn_previous.visibility = View.VISIBLE
            btn_submit.visibility = View.VISIBLE

        } else {
            btn_next.visibility = View.VISIBLE
            btn_previous.visibility = View.VISIBLE
        }


        val question: QuestionDataClass? = questions!!["question$index"]

        question?.let {
            view_que_title.text = it.description
            val optionAdaper = QuestionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdaper
        }


    }
}

