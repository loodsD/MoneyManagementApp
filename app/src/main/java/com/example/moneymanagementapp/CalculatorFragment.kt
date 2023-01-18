package com.example.moneymanagementapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import android.view.View


class CalculatorFragment : Fragment(R.layout.fragment_calculator) {
    lateinit var option : Spinner
    lateinit var editTextLoanAmount: EditText
    lateinit var editTextInterestRate: EditText
    lateinit var editTextMonthlyPay: EditText
    lateinit var price: EditText


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        option = view.findViewById(R.id.productBox)
        editTextLoanAmount = view.findViewById(R.id.editTextLoanAmount)
        editTextInterestRate = view.findViewById(R.id.editTextInterestRate)
        editTextMonthlyPay = view.findViewById(R.id.editTextMonthlyPay)
        price = view.findViewById(R.id.priceInput)

        val options = arrayOf("Car", "House", "Others")

        option.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options)


        val loanCalculate: Button = view.findViewById(R.id.loanCalculatorButton)
        val builder = AlertDialog.Builder(context)

        addTextWatcher(editTextLoanAmount, loanCalculate)
        addTextWatcher(editTextInterestRate, loanCalculate)
        addTextWatcher(editTextMonthlyPay, loanCalculate)


        loanCalculate.setOnClickListener(){
            val loanAmount = editTextLoanAmount.text.toString().toDouble()
            val interestRate = editTextInterestRate.text.toString().toDouble()
            val monthlyPay = editTextMonthlyPay.text.toString().toDouble()
            val yearToBePaid = calculateYear(loanAmount,interestRate,monthlyPay)
            if(yearToBePaid != 0){
                builder.setTitle("How many years needed to clear the loan")
                builder.setMessage("You need roughly "+yearToBePaid.toString()+" years to finish the loan")
                builder.setPositiveButton("OK") { dialog, which ->
                    onStop()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }else{
                builder.setTitle("The interest is STAKING higher than your pay")
                builder.setMessage("You need to add more monthly pay to finish the loan")
                builder.setPositiveButton("OK") { dialog, which ->
                    onStop()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        val purchasingPowerCalculate:Button = view.findViewById(R.id.purchasingPowerCalculatorButton)

        price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!price.text.toString().trim().isEmpty()){
                    purchasingPowerCalculate.isEnabled = true
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        purchasingPowerCalculate.setOnClickListener(){
            val selectedOption = options[option.selectedItemPosition].toString()
            val priceProduct = price.text.toString().toDouble()
            builder.setTitle("Your Purchasing Power")
            var powerValue : Double;
            if(selectedOption == "House"){
                powerValue = Math.ceil(priceProduct*1.3/300)
                builder.setMessage("You need to have an extra amount of RM$powerValue to own this house.")
            }else if(selectedOption == "Car"){
                powerValue = Math.ceil(priceProduct*1.3/84)
                builder.setMessage("You need to have an extra amount of RM$powerValue to own this car.")
            }else{
                powerValue = Math.ceil(priceProduct*1.3/12)
                builder.setMessage("You need to have an extra amount of RM$powerValue to own this product in 1 year.")
            }

            builder.setPositiveButton("OK") { dialog, which ->
                onStop()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    fun calculateYear(loanAmount:Double,interestRate:Double,monthlyPay:Double) : Int{
        if(loanAmount*(interestRate/100) > monthlyPay*12){
            return 0
        }
        var currentLoan = loanAmount
        var year:Int = 0
        while(currentLoan > 0){
            currentLoan = currentLoan*((100+interestRate)/100)
            currentLoan -= monthlyPay*12
            year++
        }
        return year
    }
    fun addTextWatcher(editText: EditText, button: Button) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!editTextLoanAmount.text.toString().trim().isEmpty() && !editTextInterestRate.text.toString().trim().isEmpty() && !editTextMonthlyPay.text.toString().trim().isEmpty()){
                    button.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    override fun onStop() {
        super.onStop()
        // Reset views here
        editTextLoanAmount.setText("")
        editTextInterestRate.setText("")
        editTextMonthlyPay.setText("")
        option.setSelection(0)
        price.setText("")
    }

}
