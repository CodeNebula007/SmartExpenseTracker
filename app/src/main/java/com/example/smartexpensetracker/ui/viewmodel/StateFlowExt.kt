package com.example.smartexpensetracker.ui.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> StateFlow<T>.observe(owner: LifecycleOwner, block: (T) -> Unit) {
    owner.lifecycleScope.launch { this@observe.collect { block(it) } }
}