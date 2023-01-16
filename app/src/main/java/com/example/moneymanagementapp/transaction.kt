package com.example.moneymanagementapp

class transaction {

    var id : Int = 0
    var transType : String = ""
    var transCategory : String = ""
    var reference : String = ""
    var amount : Double = 0.00

    constructor(transType:String,transCategory:String,reference:String,amount:Double){
        this.transType = transType
        this.transCategory = transCategory
        this.reference = reference
        this.amount = amount
    }
}