package com.example.moneymanagementapp

import android.annotation.SuppressLint
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

        //create a table with correspond column, ID,Type,Category,Reference,Amount
        val createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_REFERENCE + " TEXT, " +
                COL_AMOUNT + " DECIMAL(7,2))";

        //execute the create table statement
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    //insert the data
    fun insertData(transaction: transaction){

        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_TYPE,transaction.transType)
        contentValue.put(COL_CATEGORY,transaction.transCategory)
        contentValue.put(COL_REFERENCE,transaction.reference)
        contentValue.put(COL_AMOUNT,transaction.amount)

        //result is to make sure the data insert is successful
        var result = db.insert(TABLE_NAME,null,contentValue)

        //if it is failed, then send a mesesage "failed" else "Success"
        if(result == -1.toLong()){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    fun retrieveData(): MutableList<transaction>{
        var store : MutableList<transaction> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT Category, SUM(Amount) AS SUM FROM Transaction1 WHERE Type = 'Money Out' GROUP BY Category"
        val cursor= db.rawQuery(query,null)

        if(cursor.moveToFirst()){
            do{
                var trans = transaction()
                trans.transCategory = cursor.getString(0)
                trans.amount = cursor.getString(1).toDouble()
                store.add(trans)
            }while(cursor.moveToNext())
        }
        cursor.close()
        return store
    }
}