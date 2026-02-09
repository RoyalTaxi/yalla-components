@file:Suppress("ClassName")

package uz.yalla.components.foundation.error

/**
 * Sealed hierarchy of data layer errors.
 *
 * Use these error types when network or data operations fail.
 *
 * ## Usage
 *
 * ```kotlin
 * suspend fun fetchData(): Either<Data, DataError> =
 *     try {
 *         Either.Success(api.getData())
 *     } catch (e: Exception) {
 *         Either.Error(e.toDataError())
 *     }
 * ```
 */
sealed class DataError : Error() {

    /**
     * Network-related errors.
     */
    sealed class Network : DataError() {
        /** User session expired, requires re-authentication. */
        data object UNAUTHORIZED_ERROR : Network()

        /** Unexpected redirect response. */
        data object REDIRECT_RESPONSE_ERROR : Network()

        /** Client request error (4xx). */
        data object CLIENT_REQUEST_ERROR : Network()

        /** Server response error (5xx). */
        data object SERVER_RESPONSE_ERROR : Network()

        /** No internet connection available. */
        data object NO_INTERNET_ERROR : Network()

        /** Failed to parse response data. */
        data object SERIALIZATION_ERROR : Network()

        /** Connection timed out. */
        data object SOCKET_TIME_OUT_ERROR : Network()

        /** Insufficient balance for operation. */
        data object NOT_SUFFICIENT_BALANCE : Network()

        /** Unknown network error. */
        data object UNKNOWN_ERROR : Network()
    }
}
