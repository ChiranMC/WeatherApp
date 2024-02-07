package com.nibm.weather_ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Forecast : AppCompatActivity() {
    private val weatherList = ArrayList<Weather>()
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        val enteredCity = intent.getStringExtra("EXTRA_KEY")

        // Initialize RecyclerView and set adapter
        val recyclerView: RecyclerView = findViewById(R.id.idRecyclerWeathersecond)
        weatherAdapter = WeatherAdapter(weatherList)
        recyclerView.adapter = weatherAdapter

        if (enteredCity != null) {
            fetchRecyclerData(enteredCity) { jsonString ->
                if (jsonString != null) {
                    // Handle the JSON string here
                    Log.e("JSON Response", jsonString)
                    parseJsonResponse(jsonString) // Move parsing inside the callback

                    // Notify the adapter that the data set has changed
                    weatherAdapter.notifyDataSetChanged()
                } else {
                    // Handle the error
                    Log.e("Error", "Error fetching data")
                }
            }
        }
    }

    private fun parseJsonResponse(jsonResponse: String) {
        // Clear the existing weatherList before adding new data
        weatherList.clear()

        // Parse JSON and populate weatherList
        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("list")

            // Get today's date in the format "yyyy-MM-dd"
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val dtTxt = item.getString("dt_txt")

                // Check if the date is not today
                if (!filterForToday(dtTxt, todayDate)) {

                    val temp = String.format("%.2f",item.getJSONObject("main").getDouble("temp") - 273.15)
                    val icon = item.getJSONArray("weather").getJSONObject(0).getString("icon")
                    val main = item.getJSONArray("weather").getJSONObject(0).getString("main")

                    // Convert icon to drawable resource ID
                    val iconId = getIconResourceId(icon)

                    // Add Weather object to the list
                    weatherList.add(Weather(dtTxt, temp, icon, main))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getIconResourceId(icon: String): Int {
        // Convert icon string to drawable resource ID (you may need to have corresponding icons in your resources)
        // For example, if your icons are named "ic_rain", "ic_cloud", etc.
        return resources.getIdentifier("ic_$icon", "drawable", packageName)
    }

    private fun fetchRecyclerData(city: String, listener: (String?) -> Unit) {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=13743e65600c3dd86e8905a8b8f82bc0"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { data ->
                // Convert the entire JSON response to a string
                val jsonString = data.toString()

                // Invoke the listener with the JSON string
                listener.invoke(jsonString)
            },
            { error ->
                // Handle error
                Log.e("Response", error.toString())

                // Invoke the listener with null to indicate an error
                listener.invoke(null)
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

    private fun filterForToday(dateTimeString: String, todayDate: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val inputDate = dateFormat.format(dateFormat.parse(dateTimeString) ?: Date())

            return todayDate == inputDate
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}
