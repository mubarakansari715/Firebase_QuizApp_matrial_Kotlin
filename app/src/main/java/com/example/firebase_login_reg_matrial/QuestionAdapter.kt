package com.example.firebase_login_reg_matrial

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_login_reg_matrial.Models.QuestionDataClass
import com.example.firebase_login_reg_matrial.databinding.TextviewCusBinding

class QuestionAdapter(val context: Context, private val question: QuestionDataClass) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    private var options: List<String> =
        listOf(question.option1, question.option2, question.option3, question.option4)

    class QuestionViewHolder(val itemViews: TextviewCusBinding) :
        RecyclerView.ViewHolder(itemViews.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {

        return QuestionViewHolder(TextviewCusBinding.inflate(LayoutInflater.from(context),
            parent,
            false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.itemViews.tvQueView.text = options[position]

        holder.itemViews.itemViewCard.setOnClickListener {
            question.useranswer = options[position]
            notifyDataSetChanged()
        }
        if (question.useranswer == options[position]) {
            holder.itemViews.itemViewCard.setBackgroundResource(R.drawable.circle_red)
        } else if(question.useranswer != options[position]){
            holder.itemViews.itemViewCard.setBackgroundResource(R.drawable.circle_green)
        }


    }

    override fun getItemCount(): Int {
        return options.size
    }
}