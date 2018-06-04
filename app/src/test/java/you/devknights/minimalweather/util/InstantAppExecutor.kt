package you.devknights.minimalweather.util

import you.devknights.minimalweather.core.executor.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutor: AppExecutors(instant, instant, instant) {

    companion object {
        private val instant = Executor { it.run() }
    }
}