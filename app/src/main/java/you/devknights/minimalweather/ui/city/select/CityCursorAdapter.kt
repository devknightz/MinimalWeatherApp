package you.devknights.minimalweather.ui.city.select

import android.content.Context
import android.database.Cursor
import android.view.View
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.cursoradapter.widget.ResourceCursorAdapter
import you.devknights.minimalweather.R
import you.devknights.minimalweather.database.dao.CityDAO
import you.devknights.minimalweather.database.entity.City


class CityCursorAdapter(context: Context, private val cityDAO: CityDAO): ResourceCursorAdapter(context, R.layout.item_city, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
    override fun bindView(convertView: View?, context: Context?, cursor: Cursor?) {
        val cityViewHolder = CityViewHolder(convertView as View)
        val city = City(cursor?.getLong(0) as Long, cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4))

        cityViewHolder.cityNameTextView.text = city.name
    }

    override fun runQueryOnBackgroundThread(constraint: CharSequence?): Cursor? {
        var cursor: Cursor? = null

        if (constraint != null) {
            cursor = cityDAO.searchByCityName(constraint.toString())
        }

        return cursor
    }

    class CityViewHolder(itemView: View) {
        val cityNameTextView: TextView = itemView.findViewById(R.id.text_city_name)
    }
}