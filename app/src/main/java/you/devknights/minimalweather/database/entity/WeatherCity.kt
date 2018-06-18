package you.devknights.minimalweather.database.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "weather_city")
data class WeatherCity(var cityId: Long = -1,
                       var latitude: Double = 0.0,
                       var longitude: Double = 0.0,
                       var index: Int = 0) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null


    @Ignore
    var city: City? = null

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readInt()) {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(cityId)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(index)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherCity> {
        override fun createFromParcel(parcel: Parcel): WeatherCity {
            return WeatherCity(parcel)
        }

        override fun newArray(size: Int): Array<WeatherCity?> {
            return arrayOfNulls(size)
        }
    }
}