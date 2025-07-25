package com.example.apimedical

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var tvDisplay: TextView
    private lateinit var edtInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDisplay = findViewById(R.id.tvDisplay)
        edtInput = findViewById(R.id.txtInput)

        findViewById<Button>(R.id.btnGetAll).setOnClickListener {
            hideKeyboard()
            getAllLoans()
        }

        findViewById<Button>(R.id.btnGetByID).setOnClickListener {
            hideKeyboard()
            val idText = edtInput.text.toString()
            if (idText.isNotEmpty()) {
                try {
                    //try to convert input to integer
                    val id = idText.toInt()
                    getLoadByID(id)
                } catch (e: NumberFormatException) {
                    //handle cases where the input is not a valid integer
                    Toast.makeText(this, "Please enter a valid numberic Load ID. Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Please enter a Load ID.", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnCreate).setOnClickListener {
            hideKeyboard()
            createLoan()
        }
    }

    //HIDES THE KEYBOARD POP UP ON THE SCREEN
    private fun hideKeyboard() {
        val inm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inm.hideSoftInputFromWindow(edtInput.windowToken, 0)
    }

    //EXTRACTS ALL LOANS FROM API
    private fun getAllLoans() {
        //define the api wnspoint url
        val url = "https://opsc.azurewebsites.net/loans/"
        tvDisplay.text = "Fetching all loans..."

        //execute network request on a background thread
        executor.execute {
            //use fuel's httpget for the get request
            url.httpGet().responseString { _, _, result ->
                //switch to main thread to update UI
                handler.post {
                    when (result) {
                        is com.github.kittinunf.result.Result.Success -> {
                            //on success, deserialize the json string into a list of loan objects
                            val json = result.get()
                            try {
                                val loans =
                                    Gson().fromJson(json, Array<BookLoan>::class.java).toList()
                                if (loans.isNotEmpty()) {
                                    //format the output for readability
                                    //\n\n to lay display out more neatly
                                    val formattedOutput = loans.joinToString("\n\n") { loan ->
                                        "Loan ID: ${loan.loanID}\nAmount: ${loan.amount}\nMember ID: ${loan.memberID} \nMessage: ${loan.message}"
                                    }
                                    tvDisplay.text = formattedOutput
                                } else {
                                    tvDisplay.text = "No loans found"
                                }
                            } catch (e: JsonSyntaxException) {
                                //handle cases where the server response is not valid json
                                Log.e("GetAllLoans", "Json parsing error: ${e.message}")
                                tvDisplay.text = "Error. Could not parse server response."
                            }
                        }
                        is com.github.kittinunf.result.Result.Failure -> { //response from server when calling the api (response codes 200,400,500 etc)
                            //on failure log the error and show user friendly message
                            val ex = result.getException()
                            Log.e("GetAllLoans", "API Error: ${ex.message}")
                            tvDisplay.text = "Error. Could not fetch loans from server."
                        }
                    }
                }
            }
        }
    }

    //EXTRACTS LOANS THAT MATCH INPUT ID
    private fun getLoadByID(id: Int) {
        //define the api wnspoint url
        val url = "https://opsc.azurewebsites.net/loans/${id}"
        tvDisplay.text = "Fetching loans with ID: ${id}"

        //execute network request on a background thread
        executor.execute {
            //use fuel's httpget for the get request
            url.httpGet().responseString { _, response, result ->
                //switch to main thread to update UI
                handler.post {
                    if (response.statusCode == 404) {
                        tvDisplay.text = "Loan with ID ${id} not found"
                        return@post
                    }
                    when (result) {
                        is com.github.kittinunf.result.Result.Success -> {
                            //on success, deserialize the json string into a loan object
                            try {
                                val loan = Gson().fromJson(result.get(), BookLoan::class.java)
                                val formattedOutput = "Loan ID: ${loan.loanID}\nAmount: ${loan.amount}\nMember ID: ${loan.memberID} \nMessage: ${loan.message}"
                                tvDisplay.text = formattedOutput
                            } catch (e: JsonSyntaxException) {
                                //handle cases where the server response is not valid json
                                Log.e("GetLoanByID", "Json parsing error: ${e.message}")
                                tvDisplay.text = "Error. Could not parse server response."
                            }
                        }
                        is com.github.kittinunf.result.Result.Failure -> { //response from server when calling the api (response codes 200,400,500 etc)
                            //on failure log the error and show user friendly message
                            val ex = result.getException()
                            Log.e("GetLoanByID", "API Error: ${ex.message}")
                            tvDisplay.text = "Error. Could not fetch loan."
                        }
                    }
                }
            }
        }
    }

    //EXTRACTS LOANS BY MEMBER ID
    private fun getMemberID(memberID: String) {
        //define the api wnspoint url
        val url = "https://opsc.azurewebsites.net/loans/member/${memberID}"
        tvDisplay.text = "Fetching loans with member ID: ${memberID}"

        //HOMEWORK
        //execute network request on a background thread
        executor.execute {
            //use fuel's httpget for the get request
            url.httpGet().responseString { _, response, result ->
                //switch to main thread to update UI
                handler.post {
                    if (response.statusCode == 404) {
                        tvDisplay.text = "Loan with member ID ${memberID} not found"
                        return@post
                    }
                    when (result) {
                        is com.github.kittinunf.result.Result.Success -> {
                            //on success, deserialize the json string into a loan object
                            try {
                                val loan = Gson().fromJson(result.get(), BookLoan::class.java)
                                val formattedOutput = "Loan ID: ${loan.loanID}\nAmount: ${loan.amount}\nMember ID: ${loan.memberID} \nMessage: ${loan.message}"
                                tvDisplay.text = formattedOutput
                            } catch (e: JsonSyntaxException) {
                                //handle cases where the server response is not valid json
                                Log.e("GetLoanByID", "Json parsing error: ${e.message}")
                                tvDisplay.text = "Error. Could not parse server response."
                            }
                        }
                        is com.github.kittinunf.result.Result.Failure -> { //response from server when calling the api (response codes 200,400,500 etc)
                            //on failure log the error and show user friendly message
                            val ex = result.getException()
                            Log.e("GetLoanByID", "API Error: ${ex.message}")
                            tvDisplay.text = "Error. Could not fetch loan."
                        }
                    }
                }
            }
        }
    }

    //CREATE NEW DUMMY DATA LOAN
    private fun createLoan() {
        //define the api wnspoint url
        val url = "https://opsc.azurewebsites.net/loans/"
        tvDisplay.text = "Creating a new loan"

        executor.execute {
            //create new loan object to send to request body
            val newLoan = LoanPost("M6001", "1000", "Added by Android app")
            val jsonBody = Gson().toJson(newLoan)

            url.httpPost().jsonBody(jsonBody).responseString { _, response, result -> //set the request body
                handler.post {
                    when (result) {
                        //a 201 created status indicates success
                        is com.github.kittinunf.result.Result.Success -> {
                            if (response.statusCode == 201) {
                                try {
                                    val createdLoan = Gson().fromJson(result.get(), BookLoan::class.java)
                                    tvDisplay.text = "Successfully created loan: \n\nLoan ID: ${createdLoan.loanID}\nAmount: ${createdLoan.amount}"
                                } catch (e: JsonSyntaxException) {
                                    //handle cases where the server response is not valid json
                                    Log.e("CreateLoan", "Json parsing error: ${e.message}")
                                    tvDisplay.text = "Error. Could not parse server response."
                                }
                            } else {
                                tvDisplay.text = "Failed to create loan. Status ${response.statusCode}"
                            }
                        }
                        is com.github.kittinunf.result.Result.Failure -> { //response from server when calling the api (response codes 200,400,500 etc)
                            //on failure log the error and show user friendly message
                            val ex = result.getException()
                            Log.e("CreateLoan", "API Error: ${ex.message}")
                            tvDisplay.text = "Error. Could not create new loan."
                        }
                    }
                }
            }
        }
    }
}