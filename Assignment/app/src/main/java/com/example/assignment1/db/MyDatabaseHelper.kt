package com.example.assignment1.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.assignment1.model.Goal
import com.example.assignment1.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "budget_tracker.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "transactions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_VALUE = "value"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_CATEGORY = "category"
        private const val TABLE_GOALS = "goals"
        private const val COLUMN_GOAL_ID = "id"
        private const val COLUMN_GOAL_DESC = "description"
        private const val COLUMN_GOAL_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TRANSACTIONS_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_VALUE REAL,
                $COLUMN_DATE TEXT,
                $COLUMN_CATEGORY TEXT
            )
        """.trimIndent()

        db.execSQL(CREATE_TRANSACTIONS_TABLE)

        val createGoalsTable = """
        CREATE TABLE $TABLE_GOALS (
            $COLUMN_GOAL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_GOAL_DESC TEXT,
            $COLUMN_GOAL_AMOUNT REAL
        )
    """.trimIndent()

        db.execSQL(createGoalsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    fun insertTransaction(description: String, value: Double, date: String, category: String) {
        val db = writableDatabase
        val finalDate = date.ifBlank { getCurrentDate() }
        val finalValue = if (value >= 0) value else 0.0

        val values = ContentValues().apply {
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_VALUE, finalValue)
            put(COLUMN_DATE, finalDate)
            put(COLUMN_CATEGORY, category)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTransactions(): List<Transaction> {
        val db = readableDatabase
        val transactions = mutableListOf<Transaction>()
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val transaction = Transaction(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    value = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALUE)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                )
                transactions.add(transaction)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return transactions
    }

    // ✅ Deleting a transaction by ID
    fun deleteTransaction(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun insertGoal(description: String, amount: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GOAL_DESC, description)
            put(COLUMN_GOAL_AMOUNT, amount)
        }
        db.insert(TABLE_GOALS, null, values)
        db.close()
    }

    fun getAllGoals(): List<Goal> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_GOALS", null)
        val goals = mutableListOf<Goal>()

        if (cursor.moveToFirst()) {
            do {
                goals.add(
                    Goal(
                        id = cursor.getInt(0),
                        description = cursor.getString(1),
                        amount = cursor.getDouble(2)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return goals
    }
    // MyDatabaseHelper.kt

    fun deleteGoal(id: Int) {
        val db = writableDatabase
        db.delete("goals", "id = ?", arrayOf(id.toString()))
        db.close()
    }


}
