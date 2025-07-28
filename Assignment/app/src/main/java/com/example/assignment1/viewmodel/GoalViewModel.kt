package com.example.assignment1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.assignment1.db.MyDatabaseHelper
import com.example.assignment1.model.Goal

class GoalViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = MyDatabaseHelper(application)

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals = _goals.asStateFlow()

    init {
        loadGoals() //
    }

    private fun loadGoals() {
        _goals.value = dbHelper.getAllGoals()
    }

    fun refreshGoals() {
        _goals.value = dbHelper.getAllGoals()
    }

    fun addGoal(description: String, amount: Double) {
        dbHelper.insertGoal(description, amount)
        loadGoals()
    }
    // GoalViewModel.kt

    fun deleteGoal(id: Int) {
        dbHelper.deleteGoal(id)
        refreshGoals()
    }

}
