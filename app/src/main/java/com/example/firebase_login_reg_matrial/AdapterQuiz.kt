package com.example.firebase_login_reg_matrial

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_login_reg_matrial.Activities.QuestionsActivity
import com.example.firebase_login_reg_matrial.Models.Quiz
import com.example.firebase_login_reg_matrial.databinding.ItemListBinding

class AdapterQuiz(val context: Context, val listdata: List<Quiz>) :
    RecyclerView.Adapter<AdapterQuiz.QuizViewHolder>() {

    class QuizViewHolder(val itemViewBinding: ItemListBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(ItemListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val model = listdata[position]
        holder.itemViewBinding.viewTvText.text = model.title

        holder.itemViewBinding.idCardView.setOnClickListener{
            Toast.makeText(context,listdata[position].title,Toast.LENGTH_SHORT).show()
            val i = Intent(context,QuestionsActivity::class.java)
            i.putExtra("Date", listdata[position].title)
            context.startActivity(i)
        }




    }

    override fun getItemCount(): Int {
        return listdata.size
    }
}