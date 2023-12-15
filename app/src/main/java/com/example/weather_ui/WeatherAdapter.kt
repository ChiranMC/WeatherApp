package com.example.weather_ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeatherAdapter(private val weatherList : ArrayList<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weather_rv_item,
            parent, false )
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return weatherList.size
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = weatherList[position]
        //val onlyTime = currentItem.time
        //if (filterForToday(onlyTime)){
            val imageURLs = "https://openweathermap.org/img/w/" + currentItem.iconId + ".png"

            Picasso.get().load(imageURLs).into(holder.titleImage)

            //holder.titleImage.text = currentItem.iconId
            holder.tvTime.text = currentItem.time
            holder.tvWindSpeed.text = currentItem.main
            holder.tvTemperature.text = currentItem.temp
       // }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val titleImage : ImageView = itemView.findViewById(R.id.idIcon2)
        val tvTime : TextView = itemView.findViewById(R.id.idTime2)
        val tvWindSpeed : TextView = itemView.findViewById(R.id.idWindSpeed2)
        val tvTemperature : TextView = itemView.findViewById(R.id.idTemperature2)
    }

}