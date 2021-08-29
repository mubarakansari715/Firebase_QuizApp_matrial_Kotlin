package com.example.firebase_login_reg_matrial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firebase_login_reg_matrial.Activities.ProfileActivity
import com.example.firebase_login_reg_matrial.Activities.QuestionsActivity
import com.example.firebase_login_reg_matrial.Models.Quiz
import com.example.firebase_login_reg_matrial.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    lateinit var firestore: FirebaseFirestore
    private var listdata = mutableListOf<Quiz>()
    val adapter = AdapterQuiz(this, listdata)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //[Start] drawer
        DrawerNavigationView()
        //End drawer
        //firebase start
        setUpFirebase()
        // firebase end
        binding.mainRecycleView.layoutManager = GridLayoutManager(this, 2)
        binding.mainRecycleView.adapter = adapter
        setUpDatePicker()

    }

    private fun setUpDatePicker() {
        floatingActionButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                //Log.d("date", "setUpDatePicker: Dates ")
                Log.d("date", "setUpDatePicker: Save ${datePicker.headerText}")
                val i = Intent(this, QuestionsActivity::class.java)
                i.putExtra("Date", datePicker.headerText)

                startActivity(i)

            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("date", "setUpDatePicker: Negative ${datePicker.headerText}")
            }
            datePicker.addOnCancelListener {
                Log.d("date", "setUpDatePicker: Cancel${datePicker.headerText}")
            }
        }
    }

    private fun setUpFirebase() {
        firestore = FirebaseFirestore.getInstance()
        val collections = firestore.collection("quizzes")
        collections.addSnapshotListener { value, error ->
            Log.d("data", "setUpFirebase: ${value?.toObjects(Quiz::class.java).toString()}")
            //Toast.makeText(this,"${value?.toObjects(Quiz::class.java).toString()}",Toast.LENGTH_LONG).show()
            listdata.clear()
            if (value != null) listdata.addAll(value?.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun DrawerNavigationView() {
        setSupportActionBar(topAppBar)
        toggle =
            ActionBarDrawerToggle(this, DrawerLayout, R.string.drawer_open, R.string.drawer_close)
        toggle.syncState()
        main_navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    DrawerLayout.closeDrawers()
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    DrawerLayout.closeDrawers()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}