package hu.officeshoes.barcodescanner.common.threading

import android.os.Build
import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import kotlin.math.max

internal object Schedulers {

    val mainScheduler: Scheduler by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1)
            AndroidSchedulers.from(Looper.getMainLooper(), false)
        else
            AndroidSchedulers.mainThread()
    }

    val backgroundScheduler: Scheduler by lazy {
        val maxThreads = max(1, Runtime.getRuntime().availableProcessors() - 1)

        val executorService = if (maxThreads == 1) {
            Executors.newSingleThreadExecutor()
        } else {
            Executors.newFixedThreadPool(maxThreads)
        }

        Schedulers.from(executorService)
    }
}
