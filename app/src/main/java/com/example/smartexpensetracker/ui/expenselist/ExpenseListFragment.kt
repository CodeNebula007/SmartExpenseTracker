package com.example.smartexpensetracker.ui.expenselist

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartexpensetracker.R
import com.example.smartexpensetracker.ui.viewmodel.ExpenseViewModel
import java.time.LocalDate
import com.example.smartexpensetracker.ui.viewmodel.observe


class ExpenseListFragment : Fragment() {
    private val vm: ExpenseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_expense_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<RecyclerView>(R.id.rvExpenses)
        val tvEmpty = view.findViewById<TextView>(R.id.tvEmpty)
        val tvTotal = view.findViewById<TextView>(R.id.tvSelectedTotal)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        val btnGoEntry = view.findViewById<Button>(R.id.btnGoEntry)
        val btnGoReport = view.findViewById<Button>(R.id.btnGoReport)

        val adapter = ExpenseAdapter()
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        vm.state.observe(viewLifecycleOwner) { st ->
            val selected = st.selectedDate
            val list = st.all.filter { it.date == selected }
            adapter.submitList(list)
            tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            tvTotal.text = "Total: ₹${st.selectedDateTotal.toInt()}"
        }

        val today = LocalDate.now()
        datePicker.updateDate(today.year, today.monthValue - 1, today.dayOfMonth)
        datePicker.setOnDateChangedListener { _, y, m, d ->
            vm.setSelectedDate(LocalDate.of(y, m + 1, d))
        }

        btnGoEntry.setOnClickListener { findNavController().navigate(R.id.action_list_to_entry) }
        btnGoReport.setOnClickListener { findNavController().navigate(R.id.action_list_to_report) }
    }
}