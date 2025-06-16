package com.example.assignment1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.components.ThreeFieldInputDialog
import com.example.assignment1.viewmodel.TransactionViewModel

@Composable
fun TransactionScreen(viewModel: TransactionViewModel = viewModel()) {
    val transactions by viewModel.transactions.collectAsState()
    val balance by viewModel.balance.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Budget Tracker",
                fontSize = 28.sp,
                color = Color.Yellow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )

            OutlinedTextField(
                value = balance.toString(),
                onValueChange = {
                    it.toDoubleOrNull()?.let { newBalance -> viewModel.updateBalance(newBalance) }
                },
                label = { Text("Balance") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(2.dp, Color.DarkGray)
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                LazyColumn {
                    items(transactions) { transaction ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(1.dp, Color.Black)
                                .padding(8.dp)
                        ) {
                            Text("Amount: $${transaction.value}")
                            Text("Description: ${transaction.description}")
                            Text("Date: ${transaction.date}")
                            Text("Category: ${transaction.category}")
                        }

                        IconButton(onClick = { viewModel.deleteTransaction(transaction) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Transaction")
                        }
                    }
                }
            }
        }

        SmallFloatingActionButton(
            onClick = { showDialog = true },
            containerColor = Color.Cyan,
            contentColor = Color.Black,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add transaction")
        }

        if (showDialog) {
            ThreeFieldInputDialog(
                onDismiss = { showDialog = false },
                onConfirm = { amount, desc, date, category ->
                    val amountDouble = amount.toDoubleOrNull()
                    if (amountDouble != null) {
                        viewModel.addTransaction(desc, amountDouble, date, category)
                        showDialog = false
                    }
                }
            )
        }

    }
}
