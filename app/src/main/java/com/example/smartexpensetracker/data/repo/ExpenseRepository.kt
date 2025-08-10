package com.example.smartexpensetracker.data.repo

import com.example.smartexpensetracker.data.model.Expense
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    val expenses: Flow<List<Expense>>
    suspend fun add(expense: Expense)
    suspend fun getByDate(date: LocalDate): List<Expense>
}