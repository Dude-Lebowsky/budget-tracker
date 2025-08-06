package com.example.assignment1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.viewmodel.TransactionViewModel
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.foundation.Canvas
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.PI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    viewModel: TransactionViewModel = viewModel(),
    onBackPressed: () -> Unit = {}
) {
    val transactions by viewModel.transactions.collectAsState()

    // Calculate category totals (all transactions since they're stored as positive values)
    val categoryTotals = remember(transactions) {
        transactions
            .filter { it.value > 0 } // Only transactions with positive values
            .groupBy { it.category }
            .mapValues { (_, transactions) ->
                transactions.sumOf { it.value }
            }
            .filter { it.value > 0 }
            .toList()
            .sortedByDescending { it.second }
    }

    val total = categoryTotals.sumOf { it.second }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        // Top App Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    "Spending Analysis",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (categoryTotals.isNotEmpty()) {
            // Pie Chart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PieChart(
                        data = categoryTotals,
                        modifier = Modifier.size(250.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category breakdown list
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Category Breakdown",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    categoryTotals.forEachIndexed { index, (category, amount) ->
                        val percentage = if (total > 0) (amount / total * 100).toInt() else 0

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(getCategoryColor(index))
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = category,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                            Text(
                                text = "$${String.format("%.2f", amount)} ($percentage%)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))


                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Spending:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = String.format("%.2f", total),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }
                }
            }
        } else {
            // Empty state
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "No Transactions to Analyze",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Add some transactions to see your spending breakdown",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PieChart(
    data: List<Pair<String, Double>>,
    modifier: Modifier = Modifier
) {
    val total = data.sumOf { it.second }

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = minOf(canvasWidth, canvasHeight) / 2f * 0.8f
        val center = Offset(canvasWidth / 2f, canvasHeight / 2f)

        var startAngle = -90f // Start from top

        data.forEachIndexed { index, (_, value) ->
            val sweepAngle = (value / total * 360).toFloat()

            drawArc(
                color = getCategoryColor(index),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )

            // Draw border
            drawArc(
                color = Color.White,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 3.dp.toPx())
            )

            startAngle += sweepAngle
        }
    }
}

private fun getCategoryColor(index: Int): Color {
    val colors = listOf(
        Color(0xFF3498DB), // Blue - Groceries
        Color(0xFFE74C3C), // Red - Rent
        Color(0xFF2ECC71), // Green - Utilities
        Color(0xFFF39C12), // Orange - Transport
        Color(0xFF9B59B6), // Purple - Entertainment
        Color(0xFF1ABC9C), // Teal - Others
        Color(0xFFE67E22), // Dark Orange
        Color(0xFF34495E)  // Dark Blue Gray
    )
    return colors[index % colors.size]
}