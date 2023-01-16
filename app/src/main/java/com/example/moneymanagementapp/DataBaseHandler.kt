package com.example.moneymanagementapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.sql.Types.DECIMAL

val DATABASE_NAME = "MyDB1"
val TABLE_NAME = "Transaction1"
val COL_ID = "ID"
val COL_TYPE = "Type"
val COL_CATEGORY = "Category"
val COL_REFERENCE = "Reference"
val COL_AMOUNT = "Amount"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {


        val createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_REFERENCE + " TEXT, " +
                COL_AMOUNT + " DECIMAL(7,2))";

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(transaction: transaction){

        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_TYPE,transaction.transType)
        contentValue.put(COL_CATEGORY,transaction.transCategory)
        contentValue.put(COL_REFERENCE,transaction.reference)
        contentValue.put(COL_AMOUNT,transaction.amount)

        var result = db.insert(TABLE_NAME,null,contentValue)
        Log.d("tag",result.toString()+" ${transaction.transType.toString()}"
                +" ${transaction.transCategory.toString()}"+" ${transaction.reference.toString()}"
                +" ${transaction.amount.toString()}")
        if(result == -1.toLong()){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
        }
    }
}