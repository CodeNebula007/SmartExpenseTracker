package com.example.smartexpensetracker.ui.expenseentry

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartexpensetracker.R
import com.example.smartexpensetracker.data.model.Category
import com.example.smartexpensetracker.ui.viewmodel.ExpenseViewModel
import com.example.smartexpensetracker.ui.viewmodel.observe


class ExpenseEntryFragment : Fragment() {
    private val vm: ExpenseViewModel by activityViewModels()
    private var pickedUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_expense_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val spCategory = view.findViewById<Spinner>(R.id.spCategory)
        val etNotes = view.findViewById<EditText>(R.id.etNotes)
        val btnPick = view.findViewById<Button>(R.id.btnPickImage)
        val ivReceipt = view.findViewById<ImageView>(R.id.ivReceipt)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val tvTodayTotal = view.findViewById<TextView>(R.id.tvTodayTotal)
        val btnGoList = view.findViewById<Button>(R.id.btnGoList)
        val btnGoReport = view.findViewById<Button>(R.id.btnGoReport)

        vm.state.observe(viewLifecycleOwner) { state ->
            tvTodayTotal.text = "Total Spent Today: ₹${state.todayTotal.toInt()}"
        }

        btnPick.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, 1001)
        }

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val category = Category.values()[spCategory.selectedItemPosition]
            val notes = etNotes.text?.toString()
            try {
                vm.addExpense(title, amount, category, notes, pickedUri)
                Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show()
                etTitle.text.clear(); etAmount.text.clear(); etNotes.text?.clear();
                pickedUri = null; ivReceipt.setImageResource(android.R.drawable.ic_menu_gallery)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }

        btnGoList.setOnClickListener { findNavController().navigate(R.id.action_to_list) }
        btnGoReport.setOnClickListener { findNavController().navigate(R.id.action_to_report) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            pickedUri = data?.data
            view?.findViewById<ImageView>(R.id.ivReceipt)?.setImageURI(pickedUri)
        }
    }
}