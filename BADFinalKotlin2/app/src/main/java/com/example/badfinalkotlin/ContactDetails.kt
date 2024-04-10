package com.example.badfinalkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactDetails : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var btFloatEdit: FloatingActionButton
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        tvName = findViewById(R.id.tv_name)
        tvPhoneNumber = findViewById(R.id.tv_phone_number)
        btFloatEdit = findViewById(R.id.bt_float_edit)

        tvName.text = intent.getStringExtra("name").toString()
        tvPhoneNumber.text = intent.getStringExtra("phoneNumber").toString()

        //to get result from Edit Contact
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        tvName.text = data.getStringExtra("name").toString()
                        tvPhoneNumber.text = data.getStringExtra("phoneNumber").toString()
                    }
                }
            }

        btFloatEdit.setOnClickListener {
            resultLauncher.launch(Intent(this, EditContact::class.java)
                .putExtra("name", tvName.text.toString())
                .putExtra("phoneNumber", tvPhoneNumber.text.toString()))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK, Intent(this.applicationContext, MainActivity::class.java)
            .putExtra("name", tvName.text.toString())
            .putExtra("phoneNumber", tvPhoneNumber.text.toString()))
        finish()
    }
}