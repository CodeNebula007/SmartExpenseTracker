package com.example.smartexpensetracker.data.model

import android.net.Uri
import java.time.LocalDate
import java.time.LocalDateTime

data class Expense(
    val id: String,
    val title: String,
    val amount: Double,
    val category: Category,
    val notes: String?,
    val receiptUri: Uri?,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    val date: LocalDate get() = createdAt.toLocalDate()
}
