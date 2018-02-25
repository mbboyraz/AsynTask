package io.androidedu.asyntask.helper

import java.io.*
import java.net.HttpURLConnection
import java.net.URL


/******************************
 * Created by Gökhan ÖZTÜRK   |
 * 25.02.2018                 |
 * GokhanOzturk@AndroidEdu.IO |
 *****************************/

class HttpHelper {

    fun sendRequest(reqUrl: String): String {

        var response = ""

        try {
            val url = URL(reqUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            val bufferedInputStream = BufferedInputStream(conn.inputStream)

            response = convertStreamToString(bufferedInputStream)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return response
    }

    private fun convertStreamToString(inputStream: InputStream): String {

        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()

        var line = reader.readLine()

        try {
            while (line != null) {
                sb.append(line).append('\n')
                line = readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return sb.toString()
    }
}
