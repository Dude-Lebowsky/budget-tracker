package com.example.assignment1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.assignment1.db.MyDatabaseHelper
import com.example.assignment1.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = MyDatabaseHelper(application)

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _balance = MutableStateFlow(1000.0)
    val balance: StateFlow<Double> = _balance

    init {
        refreshTransactions()
    }

    fun refreshTransactions() {
        _transactions.value = dbHelper.getAllTransactions()
    }

    fun addTransaction(description: String, value: Double, date: String, category: String) {
        dbHelper.insertTransaction(description, value, date, category)
        _balance.value -= value
        refreshTransactions()
    }

    fun updateBalance(newBalance: Double) {
        _balance.value = newBalance
    }

    fun deleteTransaction(transaction: Transaction) {
        dbHelper.deleteTransaction(transaction.id)
        _balance.value += transaction.value // Restore balance when deleting
        refreshTransactions()
    }
}
