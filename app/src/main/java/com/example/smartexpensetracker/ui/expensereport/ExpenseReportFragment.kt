package com.example.smartexpensetracker.ui.expensereport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartexpensetracker.R
import com.example.smartexpensetracker.data.model.Category
import com.example.smartexpensetracker.ui.viewmodel.ExpenseViewModel
import java.io.File
import java.time.LocalDate
import com.example.smartexpensetracker.ui.viewmodel.observe


class ExpenseReportFragment : Fragment() {
    private val vm: ExpenseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_expense_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvDaily = view.findViewById<TextView>(R.id.tvDailyTotals)
        val tvCat = view.findViewById<TextView>(R.id.tvCategoryTotals)
        val btnShare = view.findViewById<Button>(R.id.btnShareCsv)
        val btnGoEntry = view.findViewById<Button>(R.id.btnGoEntry)
        val btnGoList = view.findViewById<Button>(R.id.btnGoList)

        vm.state.observe(viewLifecycleOwner) { st ->
            val last7 = (0..6).map { LocalDate.now().minusDays(it.toLong()) }.reversed()
            val dailyLines = last7.map { d ->
                val total = st.all.filter { it.date == d }.sumOf { it.amount }
                "${d}: ₹${total.toInt()}"
            }
            tvDaily.text = "Daily Totals\n" + dailyLines.joinToString("\n")

            val cats = Category.values()
            val catLines = cats.map { c ->
                val total = st.all.filter { it.category == c }.sumOf { it.amount }
                "$c: ₹${total.toInt()}"
            }
            tvCat.text = "Category Totals\n" + catLines.joinToString("\n")
        }

        btnShare.setOnClickListener { shareMockCsv() }
        btnGoEntry.setOnClickListener { findNavController().navigate(R.id.action_report_to_entry) }
        btnGoList.setOnClickListener { findNavController().navigate(R.id.action_report_to_list) }
    }

    private fun shareMockCsv() {
        val ctx = requireContext()
        val file = File(ctx.cacheDir, "report.csv")
        file.writeText("date,title,amount,category\nmock,example,0,Food")
        val uri = FileProvider.getUriForFile(ctx, ctx.packageName + ".fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Share CSV"))
    }
}