package com.example.badfinalkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddContact : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btSubmit: Button
    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((application as ContactApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        etName = findViewById(R.id.et_name)
        etPhoneNumber  = findViewById(R.id.et_phone_number)
        btSubmit  = findViewById(R.id.bt_submit)

        btSubmit.setOnClickListener{

            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent
                .putExtra("name", etName.text.toString())
                .putExtra("phoneNumber", etPhoneNumber.text.toString()))
//            contactViewModel.insert(Contact(etName.text.toString(), etPhoneNumber.text.toString()))
            finish()
        }
    }
}