package you.devknights.minimalweather.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class Clouds {

    @SerializedName("all")
    @Expose
    var all: Int = 0

}

internal class Coord {

    @SerializedName("lon")
    @Expose
    var lon: Double = 0.toDouble()
    @SerializedName("lat")
    @Expose
    var lat: Double = 0.toDouble()

}

internal class Main {

    @SerializedName("temp")
    @Expose
    var temp: Float = 0.toFloat()
    @SerializedName("pressure")
    @Expose
    var pressure: Float = 0.toFloat()
    @SerializedName("humidity")
    @Expose
    var humidity: Float = 0.toFloat()
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double = 0.toDouble()
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double = 0.toDouble()

}

internal class Sys {

    @SerializedName("type")
    @Expose
    var type: Int = 0
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("message")
    @Expose
    var message: Double = 0.toDouble()
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("sunrise")
    @Expose
    var sunrise: Long = 0
    @SerializedName("sunset")
    @Expose
    var sunset: Long = 0

}

internal class Wind {

    @SerializedName("speed")
    @Expose
    var speed: Double = 0.toDouble()
    @SerializedName("deg")
    @Expose
    var deg: Float = 0.toFloat()

}

internal class Weather {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("main")
    @Expose
    var main: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("icon")
    @Expose
    var icon: String? = null

}