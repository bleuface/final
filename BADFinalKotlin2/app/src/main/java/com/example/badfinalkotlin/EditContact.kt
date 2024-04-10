package com.example.badfinalkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.badfinalkotlin.R

class EditContact : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btSave: Button
    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((application as ContactApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        etName = findViewById(R.id.et_name)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        btSave = findViewById(R.id.bt_save)

        etName.setText(intent.getStringExtra("name"))
        etPhoneNumber.setText(intent.getStringExtra("phoneNumber"))

        btSave.setOnClickListener{
            contactViewModel.update(Contact(etName.text.toString(), etPhoneNumber.text.toString()))
            setResult(
                Activity.RESULT_OK, Intent(this.applicationContext, ContactDetails::class.java)
                .putExtra("name", etName.text.toString())
                .putExtra("phoneNumber", etPhoneNumber.text.toString()))
            finish()
        }
    }
}