package uz.yalla.components.foundation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Base ViewModel providing common functionality for all ViewModels.
 *
 * Features:
 * - Smart loading state management via [LoadingController]
 * - Centralized exception handling with error dialog support
 * - Safe coroutine scope with automatic error handling
 *
 * ## Usage
 *
 * ```kotlin
 * class MyViewModel(
 *     private val repository: MyRepository
 * ) : BaseViewModel() {
 *
 *     fun loadData() {
 *         safeScope.launchWithLoading {
 *             repository.fetchData()
 *                 .onSuccess { data -> /* handle success */ }
 *                 .onFailure { error -> handleError(error) }
 *         }
 *     }
 * }
 * ```
 *
 * @see LoadingController for loading state management details
 */
abstract class BaseViewModel : ViewModel() {

    private val loadingController = LoadingController()

    /** Loading state flow. Observe to show/hide loading indicators. */
    val loading: StateFlow<Boolean> = loadingController.loading

    private val _showErrorDialog = MutableStateFlow(false)

    /** Whether error dialog should be visible. */
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)

    /** Current error message to display in dialog. */
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val failureChannel = Channel<Throwable>(Channel.BUFFERED)

    /** Flow of unhandled exceptions for external observation. */
    val failures = failureChannel.receiveAsFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    /**
     * Safe coroutine scope with automatic exception handling.
     *
     * Exceptions thrown in this scope are caught and passed to [handleException].
     */
    protected val safeScope: CoroutineContext
        get() = viewModelScope.coroutineContext + exceptionHandler

    /**
     * Handles uncaught exceptions by showing error dialog.
     *
     * Override to customize error handling behavior.
     *
     * @param throwable The exception that occurred
     */
    protected open fun handleException(throwable: Throwable) {
        _errorMessage.value = throwable.message ?: "An unexpected error occurred"
        _showErrorDialog.value = true
        viewModelScope.launch {
            failureChannel.send(throwable)
        }
    }

    /**
     * Shows error dialog with specified message.
     *
     * @param message Error message to display
     */
    protected fun showError(message: String) {
        _errorMessage.value = message
        _showErrorDialog.value = true
    }

    /** Dismisses the currently shown error dialog. */
    fun dismissErrorDialog() {
        _showErrorDialog.value = false
        _errorMessage.value = null
    }

    /**
     * Launches coroutine with loading state management.
     *
     * Loading indicator appears after delay and stays visible for minimum time.
     *
     * @param block Suspending operation to execute
     */
    protected fun CoroutineScope.launchWithLoading(
        block: suspend () -> Unit,
    ) = launch(exceptionHandler) {
        loadingController.withLoading { block() }
    }

    /**
     * Launches coroutine with automatic exception handling.
     *
     * @param block Suspending operation to execute
     */
    protected fun CoroutineScope.launchSafe(
        block: suspend () -> Unit,
    ) = launch(exceptionHandler) { block() }
}
