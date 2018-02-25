package io.androidedu.asyntask.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import io.androidedu.asyntask.R
import io.androidedu.asyntask.adapter.KodluyoruzStudentAdapter
import io.androidedu.asyntask.helper.KodluyoruzStudentTask
import java.lang.ref.WeakReference
import java.util.*

class AsyncTaskActivity : AppCompatActivity(), View.OnClickListener {

    //View Component
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.activity_async_task_recyclerView) }
    private val btnGetStudentList by lazy { findViewById<Button>(R.id.activity_async_task_btnGetStudentList) }
    private val adapter by lazy { KodluyoruzStudentAdapter(ArrayList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)

        initEvent()
    }

    private fun initEvent() {

        btnGetStudentList.setOnClickListener(this)

        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.adapter = adapter


        val url = "https://api.myjson.com/bins/m6po9"

        val weakReference = WeakReference<AppCompatActivity>(this@AsyncTaskActivity)
        KodluyoruzStudentTask(weakReference, adapter).execute(url)
    }

    override fun onClick(view: View) {

        val url = "https://api.myjson.com/bins/m6po9"

        val weakReference = WeakReference<AppCompatActivity>(this@AsyncTaskActivity)
        KodluyoruzStudentTask(weakReference, adapter).execute(url)
    }
}
