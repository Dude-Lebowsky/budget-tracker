package com.example.assignment1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.assignment1.model.Goal

@Composable
fun GoalsSidebar(
    goals: List<Goal>,
    onAddGoalClick: (String, Double) -> Unit,
    onDeleteGoalClick: (Int) -> Unit, // Add a delete callback
    onClose: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var desc by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .background(Color.White)
                .padding(16.dp)
                .width(280.dp)
        ) {
            Text("Goals", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))

            Button(onClick = { showDialog = true }) {
                Text("Add Goal")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(goals) { goal ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(1.dp, Color.Black)
                            .padding(8.dp)
                    ) {
                        Text(goal.description, fontWeight = FontWeight.Bold)
                        Text("Target: \$${goal.amount}")

                        IconButton(onClick = { onDeleteGoalClick(goal.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Goal")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = { onClose() }) {
                Text("Close")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Goal") },
            text = {
                Column {
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Description") }
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val amt = amount.toDoubleOrNull()
                    if (desc.isNotBlank() && amt != null) {
                        onAddGoalClick(desc, amt)
                        showDialog = false
                        desc = ""
                        amount = ""
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
