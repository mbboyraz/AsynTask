package io.androidedu.asyntask.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.androidedu.asyntask.R
import java.util.*


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 25.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

class KodluyoruzStudentAdapter(contactList: ArrayList<HashMap<String, String>>) : RecyclerView.Adapter<KodluyoruzStudentViewHolder>() {

    private var contactList: ArrayList<HashMap<String, String>>? = null

    init {
        this.contactList = contactList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KodluyoruzStudentViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_kodluyoruz_student, parent, false)

        return KodluyoruzStudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KodluyoruzStudentViewHolder, position: Int) {

        val contact = contactList!![position]

        holder.txtName.text = contact["name"]
        holder.txtEmail.text = contact["email"]
        holder.txtPhone.text = contact["phone"]
    }

    override fun getItemCount(): Int {
        return contactList!!.size
    }

    fun setListAndUpdateList(contactList: ArrayList<HashMap<String, String>>) {

        this.contactList = contactList
        notifyDataSetChanged()
    }
}