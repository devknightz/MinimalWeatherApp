package you.devknights.minimalweather.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(@PrimaryKey @ColumnInfo(name = "_id") val id: Long,
                val name: String,
                val latitude: Double,
                val longitude: Double,
                val countryCode: String = "UNKNOWN")