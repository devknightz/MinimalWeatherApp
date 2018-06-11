package you.devknights.minimalweather.ui.city.select

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import you.devknights.minimalweather.R
import you.devknights.minimalweather.database.entity.City
import you.devknights.minimalweather.database.entity.WeatherCity

class WeatherCityAdapter(private val context: Context,
                         private val cities: List<WeatherCity>) : RecyclerView.Adapter<WeatherCityAdapter.CityViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount() = cities.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        city.city?.let {
            holder.bind(it)
        }
    }


    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cityName = itemView.findViewById<TextView>(R.id.text_city_name)

        fun bind(city: City) {
            cityName.text = city.name
        }
    }
}