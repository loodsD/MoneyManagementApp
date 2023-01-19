package com.example.moneymanagementapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import org.w3c.dom.Text



class SpendingFragment : Fragment(R.layout.fragment_spending) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var category1: TextView = view.findViewById(R.id.foodDesc)
        var category2: TextView = view.findViewById(R.id.transportDesc)
        var category3: TextView = view.findViewById(R.id.groceryDesc)
        var category4: TextView = view.findViewById(R.id.investDesc)
        var category5: TextView = view.findViewById(R.id.healthDesc)
        var category6: TextView = view.findViewById(R.id.othersDesc)

        val group1 = arrayOf(category1,category2,category3,category4,category5,category6)   //transaction category

        var amount1: TextView = view.findViewById(R.id.foodAmt)
        var amount2: TextView = view.findViewById(R.id.transportAmt)
        var amount3: TextView = view.findViewById(R.id.groceryAmt)
        var amount4: TextView = view.findViewById(R.id.investAmt)
        var amount5: TextView = view.findViewById(R.id.healthAmt)
        var amount6: TextView = view.findViewById(R.id.othersAmt)

        val group2 = arrayOf(amount1, amount2, amount3, amount4, amount5, amount6)   //transaction amount


        var db = DataBaseHandler(requireContext())
        var data = db.retrieveData()
        for(i in 0..data.size-1){
            var count = 0
            for(j in group1){
                if( j.text.toString() == data.get(i).transCategory){
                    group2[count].setText("RM"+data.get(i).amount.toString())
                    break
                }
                ++count
            }

        }

    }


}