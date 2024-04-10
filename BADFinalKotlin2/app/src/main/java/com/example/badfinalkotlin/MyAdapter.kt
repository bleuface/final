package com.example.badfinalkotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.badfinalkotlin.R

class MyAdapter(_onClickListener: OnClickListeners, contactViewModel: ContactViewModel): RecyclerView.Adapter<MyAdapter.ViewHolder>(), onIntentReceived{

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    private lateinit var tvName: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var clView: ConstraintLayout
    private var contactsList: ArrayList<Contact> = ArrayList()
    private lateinit var context: Context
    private var onClickListener = _onClickListener
    private var contactViewModel: ContactViewModel = contactViewModel

    interface OnClickListeners{
        fun onClick(contact: Contact, context: Context)
        fun onUpdateData()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //initialize
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
        tvName = view.findViewById(R.id.tv_layout_name)
        tvPhoneNumber = view.findViewById(R.id.tv_layout_phone_number)
        clView = view. findViewById(R.id.cl_view)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tvName.text = (contactsList[position].name)
        tvPhoneNumber.text = (contactsList[position].phoneNumber)
        clView.setOnClickListener {
            (clView.context as Activity)
                .startActivityForResult(Intent((context), ContactDetails::class.java)
                .putExtra("name", contactsList[position].name)
                .putExtra("phoneNumber", contactsList[position].phoneNumber)
                , Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun updateList(contactList: ArrayList<Contact>){
        contactsList.clear()
        contactsList = contactList
        notifyDataSetChanged()
    }
    fun addContact(contact: Contact){
        contactsList.add(contact)
        notifyDataSetChanged()
    }

    fun addContactList(contactList: List<Contact>){
        contactsList.addAll(contactList)
        notifyDataSetChanged()
    }

    fun fetchData(contactList: List<Contact>){
        contactsList.addAll(contactList)
    }

    fun deleteData(position: Int): Contact{
        val contact: Contact = contactsList[position]
        contactsList.removeAt(position)
        notifyItemRemoved(position)
        return contact
    }

    override fun onIntent(intent: Intent, resultCode: Int) {
//        contactViewModel.update(Contact(intent.getStringExtra("name").toString(), intent.getStringExtra("phoneNumber").toString()))
//        onClickListener.onUpdateData()
    }
}