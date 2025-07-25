package com.example.apimedical

data class BookLoan (
    val loanID: Int,
    val memberID: String,
    val amount: String,
    val message: String
)

data class LoanPost (
    val MemberID: String,
    val Amount: String,
    val Message: String
)