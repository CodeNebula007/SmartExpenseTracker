package com.example.smartexpensetracker.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartexpensetracker.data.model.Category
import com.example.smartexpensetracker.data.model.Expense
import com.example.smartexpensetracker.data.repo.ExpenseRepository
import com.example.smartexpensetracker.data.repo.InMemoryExpenseRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * NOTE: This version fixes a crash (NPE on selectedDate) that could happen because
 * the previous implementation referenced a property before it was initialized when
 * using SharingStarted.Eagerly. We now drive selected date via a StateFlow and
 * combine it with the repo flow — robust across init order.
 */
class ExpenseViewModel(
    private val repo: ExpenseRepository = InMemoryExpenseRepository()
) : ViewModel() {

    data class UiState(
        val all: List<Expense> = emptyList(),
        val todayTotal: Double = 0.0,
        val selectedDate: LocalDate = LocalDate.now(),
        val selectedDateTotal: Double = 0.0
    )

    private val selectedDate = MutableStateFlow(LocalDate.now())

    val state: StateFlow<UiState> =
        combine(repo.expenses, selectedDate) { list, selected ->
            val today = LocalDate.now()
            val todayTotal = list.filter { it.date == today }.sumOf { it.amount }
            val selectedTotal = list.filter { it.date == selected }.sumOf { it.amount }
            UiState(list, todayTotal, selected, selectedTotal)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState()
        )

    fun setSelectedDate(date: LocalDate) {
        selectedDate.value = date
    }

    fun addExpense(
        title: String,
        amount: Double,
        category: Category,
        notes: String?,
        receiptUri: Uri?
    ) {
        require(title.isNotBlank()) { "Title required" }
        require(amount > 0) { "Amount must be > 0" }
        val expense = Expense(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            amount = amount,
            category = category,
            notes = notes?.take(100),
            receiptUri = receiptUri,
            createdAt = LocalDateTime.now()
        )
        viewModelScope.launch { repo.add(expense) }
    }
}