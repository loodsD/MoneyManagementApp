package com.example.moneymanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigator)
        val homeFragment =  HomeFragment()
        val inputFragment = InputFragment()
        val spendingFragment = SpendingFragment()
        val calculatorFragment = CalculatorFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment,homeFragment)
            commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.mainFragment, homeFragment)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.input ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.mainFragment, inputFragment)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.spending ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.mainFragment, spendingFragment)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.calculator ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.mainFragment, calculatorFragment)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }


    }

}