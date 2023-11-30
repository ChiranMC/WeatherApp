package com.example.weather_ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var image: ImageView
    lateinit var inputCity: TextView
    private lateinit var weatherAdapter: WeatherAdapter
    private val weatherList = ArrayList<Weather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.idIcon)
        inputCity = findViewById(R.id.idEditCity)

        // Add click listener for the search ImageView
        findViewById<ImageView>(R.id.idSearch).setOnClickListener {
            val enteredCity = inputCity.text.toString()
            // Call your method to load data for the entered city
            loadCityData(enteredCity)

            // Parse JSON response and populate weatherList
            //val jsonResponse = "{\"cod\":\"200\",\"message\":0,\"cnt\":40,\"list\":[{\"dt\":1701334800,\"main\":{\"temp\":301.17,\"feels_like\":304.7,\"temp_min\":301.17,\"temp_max\":301.17,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":47},\"wind\":{\"speed\":3.88,\"deg\":261,\"gust\":4.49},\"visibility\":10000,\"pop\":0.88,\"rain\":{\"3h\":1.05},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-11-30 09:00:00\"},{\"dt\":1701345600,\"main\":{\"temp\":300.73,\"feels_like\":304.1,\"temp_min\":299.85,\"temp_max\":300.73,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1007,\"humidity\":79,\"temp_kf\":0.88},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":2.91,\"deg\":282,\"gust\":3.83},\"visibility\":9311,\"pop\":1,\"rain\":{\"3h\":3.9},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-11-30 12:00:00\"},{\"dt\":1701356400,\"main\":{\"temp\":299.9,\"feels_like\":302.59,\"temp_min\":299.26,\"temp_max\":299.9,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1009,\"humidity\":83,\"temp_kf\":0.64},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":82},\"wind\":{\"speed\":4,\"deg\":6,\"gust\":4.87},\"visibility\":8827,\"pop\":1,\"rain\":{\"3h\":3.86},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-11-30 15:00:00\"},{\"dt\":1701367200,\"main\":{\"temp\":298.83,\"feels_like\":299.73,\"temp_min\":298.83,\"temp_max\":298.83,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1009,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.69,\"deg\":24,\"gust\":5.36},\"visibility\":10000,\"pop\":0.96,\"rain\":{\"3h\":2.06},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-11-30 18:00:00\"},{\"dt\":1701378000,\"main\":{\"temp\":298.3,\"feels_like\":299.19,\"temp_min\":298.3,\"temp_max\":298.3,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1007,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.92,\"deg\":21,\"gust\":6.1},\"visibility\":10000,\"pop\":0.63,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-11-30 21:00:00\"},{\"dt\":1701388800,\"main\":{\"temp\":298.27,\"feels_like\":299.14,\"temp_min\":298.27,\"temp_max\":298.27,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":1008,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.69,\"deg\":7,\"gust\":6.49},\"visibility\":10000,\"pop\":0.4,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-01 00:00:00\"},{\"dt\":1701399600,\"main\":{\"temp\":299.62,\"feels_like\":299.62,\"temp_min\":299.62,\"temp_max\":299.62,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":1010,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":74},\"wind\":{\"speed\":4.59,\"deg\":10,\"gust\":6.09},\"visibility\":10000,\"pop\":0.48,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-01 03:00:00\"},{\"dt\":1701410400,\"main\":{\"temp\":301.36,\"feels_like\":304.67,\"temp_min\":301.36,\"temp_max\":301.36,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":69},\"wind\":{\"speed\":5.34,\"deg\":348,\"gust\":6.1},\"visibility\":10000,\"pop\":0.76,\"rain\":{\"3h\":1.41},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-01 06:00:00\"},{\"dt\":1701421200,\"main\":{\"temp\":299.4,\"feels_like\":299.4,\"temp_min\":299.4,\"temp_max\":299.4,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":1005,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":94},\"wind\":{\"speed\":5.57,\"deg\":339,\"gust\":7.48},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":1.46},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-01 09:00:00\"},{\"dt\":1701432000,\"main\":{\"temp\":299.37,\"feels_like\":299.37,\"temp_min\":299.37,\"temp_max\":299.37,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":6.51,\"deg\":3,\"gust\":8.78},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":3.85},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-01 12:00:00\"},{\"dt\":1701442800,\"main\":{\"temp\":298.37,\"feels_like\":299.25,\"temp_min\":298.37,\"temp_max\":298.37,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.73,\"deg\":16,\"gust\":7.85},\"visibility\":10000,\"pop\":0.73,\"rain\":{\"3h\":0.45},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-01 15:00:00\"},{\"dt\":1701453600,\"main\":{\"temp\":298.21,\"feels_like\":299.1,\"temp_min\":298.21,\"temp_max\":298.21,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":1007,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.1,\"deg\":13,\"gust\":8.31},\"visibility\":10000,\"pop\":0.64,\"rain\":{\"3h\":1.78},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-01 18:00:00\"},{\"dt\":1701464400,\"main\":{\"temp\":298.16,\"feels_like\":299.01,\"temp_min\":298.16,\"temp_max\":298.16,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":1005,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.6,\"deg\":5,\"gust\":8.74},\"visibility\":10000,\"pop\":0.58,\"rain\":{\"3h\":1.56},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-01 21:00:00\"},{\"dt\":1701475200,\"main\":{\"temp\":297.96,\"feels_like\":298.85,\"temp_min\":297.96,\"temp_max\":297.96,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.82,\"deg\":357,\"gust\":7.46},\"visibility\":10000,\"pop\":0.63,\"rain\":{\"3h\":2.47},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-02 00:00:00\"},{\"dt\":1701486000,\"main\":{\"temp\":298.46,\"feels_like\":299.34,\"temp_min\":298.46,\"temp_max\":298.46,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1009,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.09,\"deg\":347,\"gust\":5.48},\"visibility\":9557,\"pop\":0.88,\"rain\":{\"3h\":3.8},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-02 03:00:00\"},{\"dt\":1701496800,\"main\":{\"temp\":297.99,\"feels_like\":298.93,\"temp_min\":297.99,\"temp_max\":297.99,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":1008,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.82,\"deg\":302,\"gust\":6.49},\"visibility\":6042,\"pop\":1,\"rain\":{\"3h\":9.1},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-02 06:00:00\"},{\"dt\":1701507600,\"main\":{\"temp\":297.64,\"feels_like\":298.6,\"temp_min\":297.64,\"temp_max\":297.64,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":1005,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.58,\"deg\":307,\"gust\":7.77},\"visibility\":4549,\"pop\":1,\"rain\":{\"3h\":11.98},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-02 09:00:00\"},{\"dt\":1701518400,\"main\":{\"temp\":298.21,\"feels_like\":299.1,\"temp_min\":298.21,\"temp_max\":298.21,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":1006,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.25,\"deg\":280,\"gust\":4.4},\"visibility\":8124,\"pop\":1,\"rain\":{\"3h\":6.3},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-02 12:00:00\"},{\"dt\":1701529200,\"main\":{\"temp\":298.58,\"feels_like\":299.45,\"temp_min\":298.58,\"temp_max\":298.58,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.89,\"deg\":273,\"gust\":2.69},\"visibility\":10000,\"pop\":0.87,\"rain\":{\"3h\":7.79},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-02 15:00:00\"},{\"dt\":1701540000,\"main\":{\"temp\":297.91,\"feels_like\":298.77,\"temp_min\":297.91,\"temp_max\":297.91,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":1008,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.16,\"deg\":89,\"gust\":2.09},\"visibility\":3951,\"pop\":0.97,\"rain\":{\"3h\":7.24},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-02 18:00:00\"},{\"dt\":1701550800,\"main\":{\"temp\":297.33,\"feels_like\":298.23,\"temp_min\":297.33,\"temp_max\":297.33,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.99,\"deg\":88,\"gust\":1.84},\"visibility\":9944,\"pop\":1,\"rain\":{\"3h\":4.82},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-02 21:00:00\"},{\"dt\":1701561600,\"main\":{\"temp\":296.87,\"feels_like\":297.75,\"temp_min\":296.87,\"temp_max\":296.87,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1007,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.36,\"deg\":76,\"gust\":3.73},\"visibility\":9057,\"pop\":1,\"rain\":{\"3h\":7.84},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-03 00:00:00\"},{\"dt\":1701572400,\"main\":{\"temp\":296.95,\"feels_like\":297.79,\"temp_min\":296.95,\"temp_max\":296.95,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":1009,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.72,\"deg\":111,\"gust\":2.88},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":2},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-03 03:00:00\"},{\"dt\":1701583200,\"main\":{\"temp\":298.29,\"feels_like\":299.11,\"temp_min\":298.29,\"temp_max\":298.29,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.86,\"deg\":177,\"gust\":2.51},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":0.44},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-03 06:00:00\"},{\"dt\":1701594000,\"main\":{\"temp\":299.98,\"feels_like\":302.52,\"temp_min\":299.98,\"temp_max\":299.98,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":1006,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.7,\"deg\":230,\"gust\":6.44},\"visibility\":8695,\"pop\":0.9,\"rain\":{\"3h\":1.68},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-03 09:00:00\"},{\"dt\":1701604800,\"main\":{\"temp\":299.15,\"feels_like\":299.15,\"temp_min\":299.15,\"temp_max\":299.15,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1007,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.09,\"deg\":230,\"gust\":3.72},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":1.82},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-03 12:00:00\"},{\"dt\":1701615600,\"main\":{\"temp\":298.95,\"feels_like\":299.81,\"temp_min\":298.95,\"temp_max\":298.95,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.14,\"deg\":225,\"gust\":3.5},\"visibility\":10000,\"pop\":0.57,\"rain\":{\"3h\":1.09},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-03 15:00:00\"},{\"dt\":1701626400,\"main\":{\"temp\":298.5,\"feels_like\":299.39,\"temp_min\":298.5,\"temp_max\":298.5,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.61,\"deg\":227,\"gust\":4.18},\"visibility\":10000,\"pop\":0.88,\"rain\":{\"3h\":2.91},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-03 18:00:00\"},{\"dt\":1701637200,\"main\":{\"temp\":298.67,\"feels_like\":299.52,\"temp_min\":298.67,\"temp_max\":298.67,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":3.25,\"deg\":242,\"gust\":4.75},\"visibility\":6650,\"pop\":0.94,\"rain\":{\"3h\":4.54},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-03 21:00:00\"},{\"dt\":1701648000,\"main\":{\"temp\":298.86,\"feels_like\":299.73,\"temp_min\":298.86,\"temp_max\":298.86,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1007,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":4.09,\"deg\":245,\"gust\":5.32},\"visibility\":10000,\"pop\":0.96,\"rain\":{\"3h\":4.87},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-04 00:00:00\"},{\"dt\":1701658800,\"main\":{\"temp\":299.88,\"feels_like\":302.62,\"temp_min\":299.88,\"temp_max\":299.88,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1009,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":4.02,\"deg\":239,\"gust\":5.59},\"visibility\":9083,\"pop\":0.92,\"rain\":{\"3h\":5.1},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-04 03:00:00\"},{\"dt\":1701669600,\"main\":{\"temp\":300.18,\"feels_like\":303.25,\"temp_min\":300.18,\"temp_max\":300.18,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.81,\"deg\":247,\"gust\":7.23},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":4.04},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-04 06:00:00\"},{\"dt\":1701680400,\"main\":{\"temp\":300.33,\"feels_like\":303.4,\"temp_min\":300.33,\"temp_max\":300.33,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.65,\"deg\":257,\"gust\":7.6},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":3.58},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-04 09:00:00\"},{\"dt\":1701691200,\"main\":{\"temp\":299.67,\"feels_like\":299.67,\"temp_min\":299.67,\"temp_max\":299.67,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":1007,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.38,\"deg\":255,\"gust\":6.75},\"visibility\":8380,\"pop\":1,\"rain\":{\"3h\":4.58},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-04 12:00:00\"},{\"dt\":1701702000,\"main\":{\"temp\":299.3,\"feels_like\":299.3,\"temp_min\":299.3,\"temp_max\":299.3,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":1009,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.17,\"deg\":247,\"gust\":5.32},\"visibility\":10000,\"pop\":0.96,\"rain\":{\"3h\":3.99},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-04 15:00:00\"},{\"dt\":1701712800,\"main\":{\"temp\":299.03,\"feels_like\":299.92,\"temp_min\":299.03,\"temp_max\":299.03,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1008,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.03,\"deg\":243,\"gust\":5.45},\"visibility\":7344,\"pop\":1,\"rain\":{\"3h\":3.52},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-04 18:00:00\"},{\"dt\":1701723600,\"main\":{\"temp\":298.83,\"feels_like\":299.7,\"temp_min\":298.83,\"temp_max\":298.83,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1006,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.79,\"deg\":245,\"gust\":5.18},\"visibility\":10000,\"pop\":0.96,\"rain\":{\"3h\":3.48},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-04 21:00:00\"},{\"dt\":1701734400,\"main\":{\"temp\":298.77,\"feels_like\":299.63,\"temp_min\":298.77,\"temp_max\":298.77,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":1007,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.1,\"deg\":224,\"gust\":4.77},\"visibility\":10000,\"pop\":0.97,\"rain\":{\"3h\":3.61},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-12-05 00:00:00\"},{\"dt\":1701745200,\"main\":{\"temp\":299.79,\"feels_like\":299.79,\"temp_min\":299.79,\"temp_max\":299.79,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":1009,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.6,\"deg\":201,\"gust\":4.11},\"visibility\":10000,\"pop\":0.83,\"rain\":{\"3h\":2.22},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-05 03:00:00\"},{\"dt\":1701756000,\"main\":{\"temp\":301.01,\"feels_like\":304.47,\"temp_min\":301.01,\"temp_max\":301.01,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":1008,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.35,\"deg\":230,\"gust\":4.42},\"visibility\":10000,\"pop\":0.87,\"rain\":{\"3h\":1.37},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-12-05 06:00:00\"}],\"city\":{\"id\":1248991,\"name\":\"Colombo\",\"coord\":{\"lat\":6.9319,\"lon\":79.8478},\"country\":\"LK\",\"population\":648034,\"timezone\":19800,\"sunrise\":1701304599,\"sunset\":1701346910}}"
            fetchRecyclerData(enteredCity) { jsonString ->
                if (jsonString != null) {
                    // Handle the JSON string here
                    Log.e("JSON Response", jsonString)
                    parseJsonResponse(jsonString) // Move parsing inside the callback
                } else {
                    // Handle the error
                    Log.e("Error", "Error fetching data")
                }
            }

            // Initialize RecyclerView and set adapter
            val recyclerView: RecyclerView = findViewById(R.id.idRecyclerWeather)
            weatherAdapter = WeatherAdapter(weatherList)
            recyclerView.adapter = weatherAdapter
        }
    }

    fun filterForToday(dateTimeString: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val todayDate = dateFormat.format(Calendar.getInstance().time)
            val inputDate = dateFormat.format(dateFormat.parse(dateTimeString) ?: Date())

            return todayDate == inputDate
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }

    private fun parseJsonResponse(jsonResponse: String) {
        // Parse JSON and populate weatherList
        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("list")

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val dayFilter = item.getString("dt_txt")
                //if(filterForToday(dayFilter)){
                    val dtTxt = item.getString("dt_txt")
                    val temp = item.getJSONObject("main").getString("temp")
                    val icon = item.getJSONArray("weather").getJSONObject(0).getString("icon")
                    val main = item.getJSONArray("weather").getJSONObject(0).getString("main")

                    // Convert icon to drawable resource ID
                    val iconId = getIconResourceId(icon)

                    // Add Weather object to the list
                    weatherList.add(Weather(dtTxt, temp, iconId, main))
                //}
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

    fun fetchRecyclerData(city: String, listener: (String?) -> Unit) {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=13743e65600c3dd86e8905a8b8f82bc0"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
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

    fun loadCityData(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=13743e65600c3dd86e8905a8b8f82bc0"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { data ->
            Log.e("Response", data.toString())

            try {
                // Extract temperature from main object (in Kelvin)
                val temperatureKelvin = data.getJSONObject("main").getDouble("temp")

                // Convert Kelvin to Celsius
                val temperatureCelsius = temperatureKelvin - 273.15
                // Display the temperature in Celsius in the TextView
                findViewById<TextView>(R.id.idTemperature2).text = String.format("%.2f °C", temperatureCelsius)

                // Extract Weather Description
                val weatherArray = data.getJSONArray("weather")
                if (weatherArray.length() > 0) {
                    val main = weatherArray.getJSONObject(0).getString("main")
                    val description = weatherArray.getJSONObject(0).getString("description")
                    val condition = "$main - $description"
                    findViewById<TextView>(R.id.idIcon2).text = condition
                }

                // Extract Humidity
                val humidity = data.getJSONObject("main").getInt("humidity")
                findViewById<TextView>(R.id.idHumidity).text = "$humidity%"

                // Extract Wind Speed
                val windSpeed = data.getJSONObject("wind").getDouble("speed")
                findViewById<TextView>(R.id.idWindSpeed2).text = String.format("%.2f m/s", windSpeed)

                // Load and display weather icon (similar to your existing code)
                val imageURL = "https://openweathermap.org/img/w/" +
                        weatherArray.getJSONObject(0).getString("icon") + ".png"
                Picasso.get().load(imageURL).into(image)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }) { error ->
            Log.e("Response", error.toString())
        }
        Volley.newRequestQueue(this).add(request)
    }
}
