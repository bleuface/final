package com.example.badfinalkotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.badfinalkotlin.R
import com.example.badfinalkotlin.databinding.ActivityMainBinding


class MainActivity<ActivityMainBinding> : AppCompatActivity() {

    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((application as ContactApplication).repository)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var onIntentReceived: onIntentReceived
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //binding root
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvContacts.layoutManager = LinearLayoutManager(this)

        val lifecycleOwner = this
        // onClickListener
        val onClickListener = object: MyAdapter.OnClickListeners{
            override fun onClick(contact: Contact, context: Context) {
                context.startActivity(Intent(context, ContactDetails::class.java)
                    .putExtra("name", contact.name)
                    .putExtra("phoneNumber", contact.phoneNumber)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }

            override fun onUpdateData() {
                contactViewModel.allContacts.observe(lifecycleOwner) { contact ->
                    adapter.updateList(contact as ArrayList<Contact>)
                }
            }
        }


        //adapter
        adapter = MyAdapter(onClickListener, contactViewModel)
        contactViewModel.allContacts.observe(this) { contact ->
            adapter.updateList(contact as ArrayList<Contact>)
        }

        onIntentReceived = adapter

        // swipe functionality
        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        contactViewModel.delete(adapter.deleteData(viewHolder.absoluteAdapterPosition))
                    }
                }
            }
        }
        val techHelper = ItemTouchHelper(swipeGesture)
        techHelper.attachToRecyclerView(binding.rvContacts)

        binding.rvContacts.adapter = adapter

        //get Data from AddContact class
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    adapter.addContact(Contact(data.getStringExtra("name").toString(), data.getStringExtra("phoneNumber").toString()))
                    contactViewModel.insert((Contact(data.getStringExtra("name").toString(), data.getStringExtra("phoneNumber").toString())))
//                        contactViewModel.allContacts.observe(this) { contact ->
//                            adapter.updateList(contact as ArrayList<Contact>)
//                    }
                }
            }
        }
        binding.btFloat.setOnClickListener{
            resultLauncher.launch(Intent(this, AddContact::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            if (data != null) {
                onIntentReceived.onIntent(data, resultCode)
            }
        }
    }
}