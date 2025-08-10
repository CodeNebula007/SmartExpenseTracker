package com.example.smartexpensetracker.data.repo

import com.example.smartexpensetracker.data.model.Expense
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryExpenseRepository : ExpenseRepository {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    override val expenses = _expenses.asStateFlow()

    override suspend fun add(expense: Expense) {
        _expenses.value = _expenses.value + expense
    }

    override suspend fun getByDate(date: LocalDate): List<Expense> =
        _expenses.value.filter { it.date == date }
}