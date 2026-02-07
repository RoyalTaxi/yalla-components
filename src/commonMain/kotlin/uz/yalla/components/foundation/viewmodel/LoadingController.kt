package uz.yalla.components.foundation.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.datetime.Clock

/**
 * Controls loading state with smart timing to prevent UI flicker.
 *
 * Features:
 * - Delays showing loading indicator to avoid flash for quick operations
 * - Keeps indicator visible for minimum time once shown to prevent jarring UX
 * - Supports multiple concurrent operations
 * - Thread-safe with mutex synchronization
 *
 * ## Usage
 *
 * ```kotlin
 * class MyViewModel : ViewModel() {
 *     private val loadingController = LoadingController()
 *     val loading: StateFlow<Boolean> = loadingController.loading
 *
 *     fun loadData() {
 *         viewModelScope.launch {
 *             loadingController.withLoading {
 *                 repository.fetchData()
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @param showAfter Delay before showing loading indicator. Defaults to 400ms.
 * @param minDisplayTime Minimum time to keep indicator visible once shown. Defaults to 300ms.
 */
class LoadingController(
    private val showAfter: Duration = DEFAULT_SHOW_AFTER,
    private val minDisplayTime: Duration = DEFAULT_MIN_DISPLAY_TIME,
) {
    private val _loading = MutableStateFlow(false)

    /** Current loading state. Emits true when loading indicator should be visible. */
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val mutex = Mutex()
    private var activeOperations = 0
    private var showJob: Job? = null
    private var visibleSince: Long? = null

    /**
     * Executes [block] with loading state management.
     *
     * @param T Return type of the operation
     * @param block Suspending operation to execute
     * @return Result of [block]
     */
    suspend fun <T> withLoading(block: suspend () -> T): T = coroutineScope {
        startLoading()
        try {
            block()
        } finally {
            stopLoading()
        }
    }

    private suspend fun startLoading() {
        mutex.withLock {
            activeOperations++
            if (activeOperations == 1) {
                showJob = coroutineScope {
                    launch {
                        delay(showAfter)
                        mutex.withLock {
                            if (activeOperations > 0) {
                                _loading.value = true
                                visibleSince = currentTimeMillis()
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun stopLoading() {
        mutex.withLock {
            activeOperations--
            if (activeOperations == 0) {
                showJob?.cancel()
                showJob = null

                visibleSince?.let { startTime ->
                    val elapsed = currentTimeMillis() - startTime
                    val remaining = minDisplayTime.inWholeMilliseconds - elapsed
                    if (remaining > 0) {
                        delay(remaining)
                    }
                }

                _loading.value = false
                visibleSince = null
            }
        }
    }

    private fun currentTimeMillis(): Long =
        Clock.System.now().toEpochMilliseconds()

    companion object {
        /** Default delay before showing loading indicator. */
        val DEFAULT_SHOW_AFTER: Duration = 400.milliseconds

        /** Default minimum display time once indicator is shown. */
        val DEFAULT_MIN_DISPLAY_TIME: Duration = 300.milliseconds
    }
}
