package io.androidedu.asyntask.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import io.androidedu.asyntask.R
import io.androidedu.asyntask.adapter.KodluyoruzStudentAdapter
import io.androidedu.asyntask.helper.HttpHelper
import org.json.JSONException
import org.json.JSONObject

class AsyncTaskActivityLoaderManager : AppCompatActivity(), View.OnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<HashMap<String, String>>> {

    //View Component
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.activity_async_task_recyclerView) }
    private val btnGetStudentList by lazy { findViewById<Button>(R.id.activity_async_task_btnGetStudentList) }
    private val adapter by lazy { KodluyoruzStudentAdapter(ArrayList()) }

    private val toast by lazy { Toast(this) }

    private val loaderID = 22
    private val contactList by lazy { ArrayList<HashMap<String, String>>() }

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
        val bundle = Bundle()
        bundle.putString("URL", url)

        val loaderManager = supportLoaderManager
        val urlLoaderManager = loaderManager.getLoader<ArrayList<HashMap<String, String>>>(loaderID)
        loaderManager.initLoader(loaderID, bundle, this)
//        if (urlLoaderManager == null) {
//
//            loaderManager.initLoader(loaderID, bundle, this).forceLoad()
//        } else {
//            loaderManager.restartLoader(loaderID, bundle, this)
//        }
    }

    override fun onClick(view: View) {

//        val weakReference = WeakReference<AppCompatActivity>(this@AsyncTaskActivityLoaderManager)
//        KodluyoruzStudentTask(weakReference, adapter).execute(url)
    }

    @SuppressLint("StaticFieldLeak")
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<HashMap<String, String>>> {

        return object : AsyncTaskLoader<ArrayList<HashMap<String, String>>>(this) {

            override fun onStartLoading() {

                if (args == null) {

                    return
                }

                toast.cancel()
                Toast.makeText(this@AsyncTaskActivityLoaderManager, "İndirme devam ediyor", Toast.LENGTH_SHORT).show()

            }

            override fun loadInBackground(): ArrayList<HashMap<String, String>>? {

                val url = args?.getString("URL")

                val httpHelper = HttpHelper()

                val jsonStr = httpHelper.sendRequest(url!!)

                Log.e(ContentValues.TAG, "Response URL : " + jsonStr)

                if (jsonStr != "") {

                    try {

                        val jsonObj = JSONObject(jsonStr)

                        val contacts = jsonObj.getJSONArray("contacts")

                        for (i in 0 until contacts.length()) {

                            val jsonObject = contacts.getJSONObject(i)

                            val id = jsonObject.getString("id")
                            val name = jsonObject.getString("name")
                            val email = jsonObject.getString("email")
                            val phone = jsonObject.getString("phone")

                            val contact = HashMap<String, String>()

                            contact["id"] = id
                            contact["name"] = name
                            contact["email"] = email
                            contact["phone"] = phone

                            contactList.add(contact)
                        }
                    } catch (e: JSONException) {
                        Log.e(ContentValues.TAG, "Json parsing error: " + e.message)

                        Toast.makeText(this@AsyncTaskActivityLoaderManager, "Json parsing error: ${e.message}", Toast.LENGTH_LONG).show()
                    }

                } else {
                    Log.e(ContentValues.TAG, "Couldn't get json from server.")
                    Toast.makeText(this@AsyncTaskActivityLoaderManager, "Couldn't get json from server. Check LogCat for possible errors", Toast.LENGTH_LONG).show()
                }

                return contactList
            }
        }
    }

    override fun onLoadFinished(loader: Loader<ArrayList<HashMap<String, String>>>?, data: ArrayList<HashMap<String, String>>?) {

        adapter.setListAndUpdateList(data!!)
    }

    override fun onLoaderReset(loader: Loader<ArrayList<HashMap<String, String>>>?) {

    }


    override fun onDestroy() {
        super.onDestroy()
        supportLoaderManager.destroyLoader(loaderID)
    }
}
