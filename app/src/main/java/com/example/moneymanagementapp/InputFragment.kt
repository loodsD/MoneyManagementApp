package com.example.moneymanagementapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class InputFragment : Fragment(R.layout.fragment_input) {
    lateinit var transactionCategory : RadioGroup
    lateinit var transactionCategory2 : RadioGroup
    lateinit var storeButton : Button
    lateinit var transactionTypes : Spinner
    lateinit var  editTextSubject : EditText
    lateinit var editTextAmount : EditText
    var isChecked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionCategory = view.findViewById(R.id.radioGroup)
        transactionCategory2 = view.findViewById(R.id.radioGroup2)
        storeButton = view.findViewById(R.id.storeButton)
        transactionTypes = view.findViewById(R.id.transactionType)
        editTextSubject = view.findViewById(R.id.editTextSubject)
        editTextAmount = view.findViewById(R.id.editTextAmount)
        val builder = AlertDialog.Builder(context)
        var shouldExecute : Boolean = true
        var here : String = ""

        val transactionType = arrayOf("Money In", "Money Out")

        transactionTypes.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, transactionType)


        transactionCategory.setOnCheckedChangeListener { group, checkedId ->
            if (transactionCategory2.checkedRadioButtonId != -1 && shouldExecute) {
                shouldExecute = false
                transactionCategory2.clearCheck()
                shouldExecute = true
            }
            here = "transactionCategory"
        }

        transactionCategory2.setOnCheckedChangeListener { group, checkedId ->
            if (transactionCategory.checkedRadioButtonId != -1 && shouldExecute) {
                shouldExecute = false
                transactionCategory.clearCheck()
                shouldExecute = true
            }
            here = "transactionCategory2"
        }
        var selectedCategory = ""
        storeButton.setOnClickListener{
            val selectedType = transactionType[transactionTypes.selectedItemPosition].toString()
            val subject = editTextSubject.text.toString()
            if((transactionCategory.checkedRadioButtonId != -1 || transactionCategory2.checkedRadioButtonId != -1) &&
                subject != "" && editTextAmount.text.toString().toDouble() != 0.00 ){
                val amount = editTextAmount.text.toString().toDouble()
                if(here.equals("transactionCategory")){
                    selectedCategory = view.findViewById<RadioButton>(transactionCategory.checkedRadioButtonId).text.toString()
                }else if(here.equals("transactionCategory2")){
                    selectedCategory = view.findViewById<RadioButton>(transactionCategory2.checkedRadioButtonId).text.toString()
                }
                var transaction = transaction(selectedType,selectedCategory,subject,amount)
                var db = DataBaseHandler(requireContext())
                db.insertData(transaction)
            }else{
                builder.setTitle("Data insufficient")
                builder.setMessage("Please fill in/check all the data(No input 0 for amount)")
                builder.setPositiveButton("OK") { dialog, which ->
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

    }
}