package com.example.assignment1.model

data class Transaction(
    val id: Int = 0,  // Auto-increment primary key
    val description: String,
    val value: Double = 0.0, // Default value to 0.0
    val date: String = "", // Default to empty string if not provided
    val category: String
)
