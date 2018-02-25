package io.androidedu.asyntask.helper

import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.androidedu.asyntask.adapter.KodluyoruzStudentAdapter
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.*


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 25.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

// AsyncTask<A, B, C>
// A = doInBackground'a verilecek parametre icin.
// B = publishProgress'in tipi. dolayısıyla onProgressUpdate()'in parametresi
// C = doInBackground geri donus degeri icin ve onPostExecute parametresi icin.

class KodluyoruzStudentTask(private val weakActivity: WeakReference<AppCompatActivity>,
                            private val adapter: KodluyoruzStudentAdapter)

    : AsyncTask<String, Int, ArrayList<HashMap<String, String>>>() {

    private val contactList by lazy { ArrayList<HashMap<String, String>>() }
    private val toast by lazy { Toast(weakActivity.get()) }

    override fun onPreExecute() {
        super.onPreExecute()

        toast.cancel()
        Toast.makeText(weakActivity.get(), "İndirme Başladı", Toast.LENGTH_SHORT).show()
    }

    override fun doInBackground(vararg param: String): ArrayList<HashMap<String, String>> {

        val httpHelper = HttpHelper()

        val url = param[0]

        val jsonStr = httpHelper.sendRequest(url)

        Log.e(TAG, "Response URL : " + jsonStr)

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
                Log.e(TAG, "Json parsing error: " + e.message)

                weakActivity.get()?.runOnUiThread(Runnable {
                    Toast.makeText(weakActivity.get(), "Json parsing error: ${e.message}", Toast.LENGTH_LONG).show()
                })
            }

        } else {
            Log.e(TAG, "Couldn't get json from server.")
            weakActivity.get()?.runOnUiThread(Runnable {
                Toast.makeText(weakActivity.get(), "Couldn't get json from server. Check LogCat for possible errors", Toast.LENGTH_LONG).show()
            })
        }

        var counter = 0
        while (counter < 101) {
            try {
                publishProgress(counter)
                Thread.sleep(1000)
            } catch (e: InterruptedException) {

                Log.e("AsynTask", e.message)
            }

            counter += 10
        }

        return contactList
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)

        val currentProgress = values[0]

        toast.cancel()
        Toast.makeText(weakActivity.get(), "İndirme devam ediyor % $currentProgress", Toast.LENGTH_SHORT).show()
    }

    override fun onPostExecute(result: ArrayList<HashMap<String, String>>) {
        super.onPostExecute(result)

        adapter.setListAndUpdateList(result)
    }
}