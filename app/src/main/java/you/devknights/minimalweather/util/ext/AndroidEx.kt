package you.devknights.minimalweather.util.ext

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment


fun Fragment.isPermissionGranted(permission: Array<String>): Boolean {
    return this.context?.isPermissionGranted(permission) == true
}


fun Context.isPermissionGranted(permission: Array<String>): Boolean {
    var hasPermission = true

    permission.map {
        hasPermission = ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            return@map
        }
    }

    return hasPermission
}