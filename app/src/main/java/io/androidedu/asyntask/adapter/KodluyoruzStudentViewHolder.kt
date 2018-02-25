package io.androidedu.asyntask.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.androidedu.asyntask.R


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 25.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

class KodluyoruzStudentViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txtName by lazy { itemView.findViewById<TextView>(R.id.adapter_item_kodluyoruz_student_txtName) }
    val txtEmail by lazy { itemView.findViewById<TextView>(R.id.adapter_item_kodluyoruz_student_txtEmail) }
    val txtPhone by lazy { itemView.findViewById<TextView>(R.id.adapter_item_kodluyoruz_student_txtPhone) }
}
