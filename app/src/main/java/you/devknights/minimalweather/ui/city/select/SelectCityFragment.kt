package you.devknights.minimalweather.ui.city.select

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_select_city.*
import you.devknights.minimalweather.R
import you.devknights.minimalweather.database.dao.CityDAO
import you.devknights.minimalweather.database.entity.City
import you.devknights.minimalweather.database.entity.WeatherCity
import you.devknights.minimalweather.di.Injectable
import you.devknights.minimalweather.ui.city.CityViewModel
import javax.inject.Inject

class SelectCityFragment : Fragment(), Injectable {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var cityDAO: CityDAO

    private lateinit var cityViewModel: CityViewModel


    private lateinit var weatherCityLiveData: LiveData<List<WeatherCity>>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_city, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityViewModel = ViewModelProviders.of(this, factory)[CityViewModel::class.java]

        weatherCityLiveData = cityViewModel.getAllWeatherCity()

        weatherCityLiveData.observe(this, Observer {
            it?.let {
                recycler_view_city_list.adapter = WeatherCityAdapter(context as Context, it)
                recycler_view_city_list.layoutManager = LinearLayoutManager(context as Context)
                progress_bar_loading.visibility = View.GONE
                recycler_view_city_list.visibility = View.VISIBLE
            }
        })

        setUpSearchView()
    }

    private fun setUpSearchView() {
        val searchView: SearchView = city_search_view
//        val searchAutoComplete = searchView.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        val cityCursorAdapter = CityCursorAdapter(context as Context, cityDAO)
        searchView.suggestionsAdapter = cityCursorAdapter

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                val cursor = cityCursorAdapter.cursor
                cursor?.let {
                    cursor.moveToPosition(position)
                    val city = City(cursor.getLong(0) as Long, cursor.getString(1),
                            cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4))
                    cityViewModel.addToWeatherCity(city)
                }
                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = cityCursorAdapter.cursor
                cursor?.let {
                    cursor.moveToPosition(position)
                    val city = City(cursor.getLong(0) as Long, cursor.getString(1),
                            cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4))
                    cityViewModel.addToWeatherCity(city)
                }
                return true
            }
        })
    }
}