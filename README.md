<h2><b>Mobile weather application sample designed to provide accurate information regarding the weather</b></h2>

<h3>1. Introduction</h3>

This project introduces ‘Mr. Storm’, an all-in-one sample mobile weather application designed to provide accurate and convenient information regarding the weather. Our app utilizes global weather forecasting information, to find and pinpoint meteorological information such as temperature, wind speed, humidity and present and future weather conditions. Using our app users are able to look at weather conditions present in any city throughout the world with probable forecasting predictions accurate up to 5 days.



<h3>2. Functionalities</h3>

1.	Location Detection:
•	Automatically detects and displays the user's current location upon app launch.
2.	Current Weather Details:
•	Retrieves and presents real-time weather information including temperature, windspeed, humidity and more using the Current Weather Data OpenWeather API.
3.	 Five-Day Weather Forecast:
•	Displays detailed weather forecasts for the upcoming five days, allowing users to plan activities or events accordingly using the 5 Day / 3 Hour Forecast OpenWeather API.
4.	 Multiple Location Management:
•	Enables users to add, remove, and manage multiple locations, facilitating quick access to weather details for various places of interest.
5.	 User-Friendly Interface:
•	Provides an intuitive and visually appealing user interface for seamless navigation and easy access to weather information across different screens. This application consists of multiple layouts and sub components such as Recycler Views for quick accessible weather information.



<h3>3.Tech stack</h3>

The following mobile application utilizes multiple technologies, dependencies and external libraries to provide better functionality and services within the application.<br><br> 
<b>The dependencies include:</b><br>
•	implementation("com.android.volley:volley:1.2.1")<br>
•	implementation("com.squareup.picasso:picasso:2.8")<br>
•	implementation ("com.google.android.gms:play-services-location:21.0.1")<br>
<br>
<b>The libraries include:</b><br>
•	AndroidX Libraries:<br>
o	androidx.appcompat.app.AppCompatActivity- This is part of the AndroidX library that provides compatibility to Android framework APIs.<br>
o	android.os.Bundle: This class is used to pass data between the relevant activities.<br>
o	android.util.Log: This class is used for logging messages.<br>	
o	android.widget.ImageView: This class is used to display images within the application.<br>
o	android.widget.TextView: This class is used to display text within the application.<br>
o	androidx.recyclerview.widget.RecyclerView: This is an important layout component that provides a way to dynamically display multiple components within a parent component.<br>
<br>
<b>•	Volley Library:</b><br>
o	com.android.volley.Request: This is a part of the Volley library, that uses a class for network requests.<br>
o	com.android.volley.Response: This is a part of the Volley library, that uses an interface class for network responses.<br>
o	com.android.volley.toolbox.JsonObjectRequest: This library, manages JSON object requests.<br>
<br>
<b>•	Picasso Library:</b><br>
o	com.squareup.picasso.Picasso: This class is part of the Picasso library and is used for loading images within the application.<br>
<br>
<b>•	JSON Libraries:</b><br>
o	org.json.JSONException: This class handles exceptions related to JSON-objects.<br>
o	org.json.JSONObject: This class represents JSON objects.<br>



<h3>4. References</h3>
<b>APIS Used:</b><br>
Current Weather Data API - https://api.openweathermap.org/data/2.5/weather?q=$city&appid= $api_key <br>
5 Day / 3 Hour Forecast API -https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=$api_key
