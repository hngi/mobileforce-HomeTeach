package com.example.hometeach.datamodel

import android.content.Context
import org.json.JSONException
import org.json.JSONObject

class OngoingClassModel(
    val subject: String,
    val date: String,
    val time: String,
    val tutorName: String,
    val tutorImage:String,
    val progress: Int,
    val tutorSubject: String) {

    companion object {

        fun getClassesFromFile(filename: String, context: Context): ArrayList<OngoingClassModel> {
            val classList = ArrayList<OngoingClassModel>()

            try {
                // Load data
                val jsonString = loadJsonFromAsset("data.json", context)
                val json = JSONObject(jsonString)
                val ongoingclass = json.getJSONArray("classes")

                // Get Recipe objects from data
                (0 until ongoingclass.length()).mapTo(classList) {
                    OngoingClassModel(ongoingclass.getJSONObject(it).getString("subject"),
                        ongoingclass.getJSONObject(it).getString("date"),
                        ongoingclass.getJSONObject(it).getString("time"),
                        ongoingclass.getJSONObject(it).getString("tutorName"),
                        ongoingclass.getJSONObject(it).getString("tutorImage"),
                        ongoingclass.getJSONObject(it).getInt("progress"),
                        ongoingclass.getJSONObject(it).getString("tutorSubject"))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return classList
        }

        private fun loadJsonFromAsset(filename: String, context: Context): String? {
            var json: String? = null

            try {
                val inputStream = context.assets.open(filename)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (ex: java.io.IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }
    }
}