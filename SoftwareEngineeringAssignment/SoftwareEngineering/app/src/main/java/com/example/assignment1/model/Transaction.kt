package com.example.assignment1.model

data class Transaction(
    val id: Int = 0,
    val description: String,
    val value: Double = 0.0,
    val date: String = "",
    val category: String
)
